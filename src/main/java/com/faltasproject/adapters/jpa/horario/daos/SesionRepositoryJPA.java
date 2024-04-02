package com.faltasproject.adapters.jpa.horario.daos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.horario.entities.SesionEntity;

import jakarta.transaction.Transactional;

public interface SesionRepositoryJPA extends JpaRepository<SesionEntity, Long> {
	Optional<SesionEntity> findByReferencia(String referencia);
	@Transactional
	void deleteByReferencia(String referencia);
}
