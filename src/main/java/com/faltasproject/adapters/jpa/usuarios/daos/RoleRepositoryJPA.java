package com.faltasproject.adapters.jpa.usuarios.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.usuarios.entities.RoleEntity;
import com.faltasproject.adapters.jpa.usuarios.entities.RoleEnum;
import java.util.Optional;


public interface RoleRepositoryJPA extends JpaRepository<RoleEntity, Long> {	
	Optional<RoleEntity> findByRoleEnum(RoleEnum roleEnum);
}
