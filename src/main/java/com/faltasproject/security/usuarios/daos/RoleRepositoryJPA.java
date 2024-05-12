package com.faltasproject.security.usuarios.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.security.usuarios.entity.RoleEntity;

import java.util.Optional;


public interface RoleRepositoryJPA extends JpaRepository<RoleEntity, Long> {	
	Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
}
