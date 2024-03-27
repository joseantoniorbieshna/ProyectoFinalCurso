package com.faltasproject.adapters.jpa.clases.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.domain.models.clases.Materia;

import jakarta.transaction.Transactional;

public interface CursoRepositoryJPA extends JpaRepository<CursoEntity, Long> {
	List<CursoEntity> findByNombreContainingIgnoreCase(String search);
	Optional<CursoEntity> findByReferencia(Long referencia);
	@Transactional
	void deleteByReferencia(Long referencia);
}
