package com.faltasproject.security.usuarios.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.security.usuarios.daos.RoleRepositoryJPA;
import com.faltasproject.security.usuarios.daos.UserRepositoryJPA;
import com.faltasproject.security.usuarios.dtos.AuthCreateUser;
import com.faltasproject.security.usuarios.dtos.AuthLoginRequest;
import com.faltasproject.security.usuarios.dtos.AuthReponse;
import com.faltasproject.security.usuarios.entity.RoleEntity;
import com.faltasproject.security.usuarios.entity.RoleEnum;
import com.faltasproject.security.usuarios.entity.UserEntity;
import com.faltasproject.security.utils.JwtUtils;

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
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		UserEntity userEntity = userRepositoryJPA.findByUsername(username)
				.orElseThrow(()-> new UsernameNotFoundException("El usuario "+username+" no existe"));
		
		List<SimpleGrantedAuthority> authoritiesList = new ArrayList<>();
		authoritiesList.add(new SimpleGrantedAuthority( "ROLE_".concat(userEntity.getStringRole() )));
		
		return new User(userEntity.getUsername(),
				userEntity.getPassword(),
				userEntity.isEnabled(),
				userEntity.isAccountNoExpired(),
				userEntity.isCredentialNoExpired(),
				userEntity.isAccountNoLocked(),
				authoritiesList);
	}

	public AuthReponse loginUser(AuthLoginRequest authLoginRequest) {
		String username = authLoginRequest.username();
		String password = authLoginRequest.password();
		
		Authentication authentication = this.authenticate(username,password);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String accessToken = jwtUtils.createToken(authentication);
		
		AuthReponse authReponse = new AuthReponse(username, "Usuario logeado con exito", accessToken, true);
		return authReponse;
	}

	private Authentication authenticate(String username, String password) {
		UserDetails userDetails = loadUserByUsername(username);
		// VALIDAMOS SI EXISTE EL USER
		if(userDetails==null) {
			throw new BadCredentialsException("password o user invalido");
		}
		// VALIDAMOS SI ES LA MISMA CONTRASENNA
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Incorrect Password");
        }
		return new UsernamePasswordAuthenticationToken(username, userDetails.getPassword(),userDetails.getAuthorities());
	}

	public AuthReponse createUser(@Valid AuthCreateUser authCreateUser) {
		Optional<RoleEntity> roleEntity = roleRepositoryJPA.findByRoleEnum(RoleEnum.USER);
		if(!roleEntity.isPresent()) {
			throw new NotFoundException("No existe el role USER");
		}
		UserEntity userEntity = new UserEntity(authCreateUser.username(), authCreateUser.password(), true, true, true, true,roleEntity.get());
		userEntity = userRepositoryJPA.save(userEntity);
		
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		authorities.add( new SimpleGrantedAuthority("ROLE_".concat(userEntity.getStringRole())) );
		
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = new UsernamePasswordAuthenticationToken(userEntity.getUsername(), userEntity.getPassword(),authorities);
		String accessToken = jwtUtils.createToken(authentication);
		
		AuthReponse authReponse = new AuthReponse(userEntity.getUsername(), "Usuario creado con exito", accessToken, true);
		return authReponse;
	}
	
    public String getCurrentUsername() {
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new ConflictException("Algo salió mal a la hora de obtener el usuario");
        }

        String username = authentication.getName();
        return username;
    }
    
    public void assertUserLoggedCanEditProfesor(String referenciaProfesor) {
    	//TODO comprobar si es admin o si la referencia del profesor es la misma que el logeado
    }
	
}
