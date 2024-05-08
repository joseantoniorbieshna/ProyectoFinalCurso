package com.faltasproject.security.usuarios.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.security.usuarios.entity.UserEntity;

import java.util.Optional;


public interface UserRepositoryJPA extends JpaRepository<UserEntity, Long> {
	Optional<UserEntity> findByUsername(String username);
}