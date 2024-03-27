package com.faltasproject.adapters.jpa.clases.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.clases.entities.AulaEntity;

import jakarta.transaction.Transactional;

public interface AulaRepositoryJPA extends JpaRepository<AulaEntity, Long> {
	List<AulaEntity> findByNombreContainingIgnoreCase(String search);
	Optional<AulaEntity> findByReferencia(Long referencia);
	@Transactional
	void deleteByReferencia(Long referencia);
}
