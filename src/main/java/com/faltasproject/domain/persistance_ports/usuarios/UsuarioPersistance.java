package com.faltasproject.domain.persistance_ports.usuarios;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.domain.models.usuario.Usuario;

@Repository
public interface UsuarioPersistance {
	
	Usuario create(Usuario usuario);
	
	Usuario update(String username,Usuario usuario);
	
	Stream<Usuario> readAllByRole(RoleEnum role);
	
	Stream<Usuario> readAll();
	
	void delete(String username);
	
	Usuario readByUsername(String username);

	boolean existUsername(String username);
}
