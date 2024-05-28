package com.faltasproject.security.usuarios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.security.usuarios.daos.RoleRepositoryJPA;
import com.faltasproject.security.usuarios.daos.UserRepositoryJPA;
import com.faltasproject.security.usuarios.dtos.AuthCreateUser;
import com.faltasproject.security.usuarios.dtos.AuthLoginRequest;
import com.faltasproject.security.usuarios.dtos.AuthReponse;
import com.faltasproject.security.usuarios.dtos.ChangePasswordByUserNameDTO;
import com.faltasproject.security.usuarios.dtos.ChangePasswordProfesorDTO;
import com.faltasproject.security.usuarios.dtos.UserInfo;
import com.faltasproject.security.usuarios.entity.RoleEntity;
import com.faltasproject.security.usuarios.entity.UsuarioEntity;
import com.faltasproject.security.utils.JwtUtils;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserRepositoryJPA userRepositoryJPA;

	@Autowired
	private RoleRepositoryJPA roleRepositoryJPA;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ProfesorRepositoryJPA profesorRepositoryJPA;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UsuarioEntity userEntity = userRepositoryJPA.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("El usuario " + username + " no existe"));

		List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
		authoritiesList.add(new SimpleGrantedAuthority("ROLE_".concat(userEntity.getStringRole())));

		return new User(userEntity.getUsername(), userEntity.getPassword(), userEntity.isEnabled(),
				userEntity.isAccountNoExpired(), userEntity.isCredentialNoExpired(), userEntity.isAccountNoLocked(),
				authoritiesList);
	}

	public AuthReponse loginUser(AuthLoginRequest authLoginRequest) {
		String username = authLoginRequest.username();
		String password = authLoginRequest.password();

		Authentication authentication = this.authenticate(username, password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtUtils.createToken(authentication);

		return new AuthReponse(username, "Usuario logeado con exito", accessToken, true);
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = loadUserByUsername(username);
		// VALIDAMOS SI EXISTE EL USER
		if (userDetails == null) {
			throw new BadCredentialsException("password o user invalido");
		}
		// VALIDAMOS SI ES LA MISMA CONTRASENNA
		if (!passwordEncoder.matches(password, userDetails.getPassword())) {
			throw new BadCredentialsException("Incorrect Password");
		}
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),
				userDetails.getAuthorities());
	}

	@Transactional
	public AuthReponse createUser(@Valid AuthCreateUser authCreateUser) {
		Optional<RoleEntity> roleEntity = roleRepositoryJPA.findByRoleEnum(RoleEnum.USER);
		if (!roleEntity.isPresent()) {
			throw new NotFoundException("No existe el role USER");
		}

		if (existUserByUsername(authCreateUser.username())) {
			throw new ConflictException("Ya existe el usuario con el username '" + authCreateUser.username() + "'");
		}

		Optional<ProfesorEntity> profesor = profesorRepositoryJPA.findByReferencia(authCreateUser.referenciaProfesor());

		if (!profesor.isPresent()) {
			throw new NotFoundException(
					"El profesor con la referencia " + authCreateUser.referenciaProfesor() + "no existe");
		}

		ProfesorEntity profesorPestistance = profesor.get();
		if (profesorPestistance.getUsuario() != null) {
			throw new ConflictException("El profesor ya tiene asignado un usuario");
		}

		UsuarioEntity userEntity = new UsuarioEntity(authCreateUser.username(),
				passwordEncoder.encode(authCreateUser.password()), true, true, true, true, roleEntity.get());

		userEntity = userRepositoryJPA.save(userEntity);

		profesorPestistance.setUsuario(userEntity);
		profesorRepositoryJPA.save(profesorPestistance);

		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority("ROLE_".concat(userEntity.getStringRole())));

		Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(),
				userEntity.getPassword(), authorities);
		String accessToken = jwtUtils.createToken(authentication);

		return new AuthReponse(userEntity.getUsername(), "Usuario creado con exito", accessToken,
				true);
	}

	public String getCurrentUsername() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			throw new ConflictException("Algo salió mal a la hora de obtener el usuario");
		}

		return authentication.getName();
	}

	public UserInfo getuserInfo() {
		Optional<UsuarioEntity> userEntity = userRepositoryJPA.findByUsername(getCurrentUsername());

		if (!userEntity.isPresent()) {
			throw new NotFoundException("No se ha encontrado el usuario");
		}
		UsuarioEntity userPersistance = userEntity.get();

		ProfesorEntity profesor = userPersistance.getProfesor();

		String referenciaProfesor = null;
		if (profesor != null) {
			referenciaProfesor = profesor.getReferencia();
		}

		return new UserInfo(userPersistance.getUsername(), referenciaProfesor, userPersistance.getStringRole());
	}
	
	public void changePasswordByreferenciaProfesor(ChangePasswordProfesorDTO changePasswordProfesorDTO) {
		assertForUserRoleAndIsTheSameReferenciaProfesor(changePasswordProfesorDTO.referenciaProfesor());
		
		UsuarioEntity userEntity = userRepositoryJPA.findByProfesorReferencia(changePasswordProfesorDTO.referenciaProfesor())
				.orElseThrow(()->new ConflictException("No se ha encontrado al usuario"));
		
		String passwordEncode = passwordEncoder.encode(changePasswordProfesorDTO.password());
		
		userEntity.setPassword(passwordEncode);
		userRepositoryJPA.save(userEntity);
				
	}
	
	public void changePasswordByUsername(ChangePasswordByUserNameDTO changePasswordByUserNameDTO) {
		assertForUserRoleAndIsTheSameUsuario(changePasswordByUserNameDTO.username());
		
		UsuarioEntity userEntity = userRepositoryJPA.findByUsername(changePasswordByUserNameDTO.username())
				.orElseThrow(()->new ConflictException("No se ha encontrado al usuario"));
		
		String passwordEncode = passwordEncoder.encode(changePasswordByUserNameDTO.password());
		
		userEntity.setPassword(passwordEncode);
		userRepositoryJPA.save(userEntity);
				
	}

	public void assertForUserRoleAndIsTheSameReferenciaProfesor(String referenciaProfesor) {
		UserInfo userInfo = getuserInfo();
		if (userInfo.role().equals(RoleEnum.USER.toString())
				&& !referenciaProfesor.equals(userInfo.referenciaProfesor())) {
			throw new ConflictException("Error no eres el usuario introducido");
		}
	}
	
	public void assertForUserRoleAndIsTheSameUsuario(String username) {
		UserInfo userInfo = getuserInfo();
		if (userInfo.role().equals(RoleEnum.USER.toString())
				&& !username.equals(userInfo.username())) {
			throw new ConflictException("Error no eres el usuario introducido");
		}
	}

	private boolean existUserByUsername(String username) {
		return userRepositoryJPA.findByUsername(username).isPresent();
	}
}
