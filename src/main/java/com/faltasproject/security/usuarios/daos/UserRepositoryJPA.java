package com.faltasproject.security.usuarios.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.security.usuarios.entity.UsuarioEntity;

import java.util.List;
import java.util.Optional;
import com.faltasproject.security.usuarios.entity.RoleEntity;



public interface UserRepositoryJPA extends JpaRepository<UsuarioEntity, Long> {
	Optional<UsuarioEntity> findByProfesorReferencia(String referencia);
	Optional<UsuarioEntity> findByUsername(String username);
	List<UsuarioEntity> findAllByRole(RoleEntity role);
	void deleteAllByRole(RoleEntity role);
	void deleteByUsername(String username);
}
