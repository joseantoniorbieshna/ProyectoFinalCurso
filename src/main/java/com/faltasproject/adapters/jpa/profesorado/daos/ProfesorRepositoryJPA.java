package com.faltasproject.adapters.jpa.profesorado.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;

import jakarta.transaction.Transactional;

import java.util.Optional;


public interface ProfesorRepositoryJPA extends JpaRepository<ProfesorEntity, Long> {
	Optional<ProfesorEntity> findByReferencia(String referencia);
	@Transactional
	void deleteByReferencia(String referencia);
}
