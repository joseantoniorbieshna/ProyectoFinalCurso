package com.faltasproject.security.usuarios.persistance;

import java.util.Optional;
import java.util.stream.Stream;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.exceptions.NotFoundException;
import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.domain.models.usuario.Usuario;
import com.faltasproject.domain.persistance_ports.usuarios.UsuarioPersistance;
import com.faltasproject.security.usuarios.daos.RoleRepositoryJPA;
import com.faltasproject.security.usuarios.daos.UserRepositoryJPA;
import com.faltasproject.security.usuarios.entity.RoleEntity;
import com.faltasproject.security.usuarios.entity.UsuarioEntity;

public class UsuarioPersistanceJPA implements UsuarioPersistance {
	
	private UserRepositoryJPA userRepositoryJPA;
	private RoleRepositoryJPA roleRepositoryJPA;

	
	
	public UsuarioPersistanceJPA(UserRepositoryJPA userRepositoryJPA, RoleRepositoryJPA roleRepositoryJPA) {
		super();
		this.userRepositoryJPA = userRepositoryJPA;
		this.roleRepositoryJPA = roleRepositoryJPA;
	}

	@Override
	public Usuario create(Usuario usuario) {
		Optional<RoleEntity> role = roleRepositoryJPA.findByRoleEnum(usuario.getRole());
		if(!role.isPresent()) {
			throw new NotFoundException("Introduce un rol disponible");
		}
		UsuarioEntity userPersistance = new UsuarioEntity(usuario);
		userPersistance.setRole(role.get());
		
		return userRepositoryJPA.save(userPersistance).toUsuario();
	}

	@Override
	public Usuario update(String username, Usuario usuario) {
		Optional<UsuarioEntity> usuarioOriginal = userRepositoryJPA.findByUsername(username);
		Optional<UsuarioEntity> usuarioCambiar = userRepositoryJPA.findByUsername(username);
		
		if(!usuarioOriginal.isPresent()) {
			throw new NotFoundException(getMessagetUserNotExist(username));
		}
		if(!usuario.getUsername().equals(username) && usuarioCambiar.isPresent()) {
			throw new ConflictException("El usuario al que quiere cambiar '"+username+ "' ya existe");
		}
		Optional<RoleEntity> role = roleRepositoryJPA.findByRoleEnum(usuario.getRole());
		if(!role.isPresent()) {
			throw new NotFoundException("El rol introducido no existe");
		}
		UsuarioEntity usuarioPersistance = usuarioOriginal.get();
		usuarioPersistance.fromUsuario(usuario);
		usuario.setRole(role.get().getRoleEnum());
		
		return userRepositoryJPA.save(usuarioPersistance).toUsuario();
	}

	@Override
	public Stream<Usuario> readAllByRole(RoleEnum role) {
		RoleEntity rolePersistance = roleRepositoryJPA.findByRoleEnum(role)
				.orElseThrow(()->  new NotFoundException("El rol introducido no existe"));
		
		return userRepositoryJPA.findAllByRole(rolePersistance)
				.stream()
				.map(UsuarioEntity::toUsuario);
	}

	@Override
	public Stream<Usuario> readAll() {
		return userRepositoryJPA.findAll().stream()
				.map(UsuarioEntity::toUsuario);
	}

	@Override
	public void delete(String username) {
		Optional<UsuarioEntity> userPersistance = userRepositoryJPA.findByUsername(username);
		if(!userPersistance.isPresent()) {
			throw new NotFoundException(getMessagetUserNotExist(username));
		}
		userRepositoryJPA.deleteByUsername(username);
	}

	@Override
	public Usuario readByUsername(String username) {
		Optional<UsuarioEntity> userPersistance = userRepositoryJPA.findByUsername(username);
		if(!userPersistance.isPresent()) {
			throw new NotFoundException(getMessagetUserNotExist(username));
		}
		return userRepositoryJPA.save(userPersistance.get()).toUsuario();
	}

	@Override
	public boolean existUsername(String username) {
		Optional<UsuarioEntity> userPersistance = userRepositoryJPA.findByUsername(username);
		return userPersistance.isPresent();
	}

	private String getMessagetUserNotExist(String username){
		return "El usuario '"+username+ "' no existe";
	}
}
