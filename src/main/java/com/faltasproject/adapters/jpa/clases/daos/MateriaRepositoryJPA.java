package com.faltasproject.adapters.jpa.clases.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

import jakarta.transaction.Transactional;

public interface MateriaRepositoryJPA extends JpaRepository<MateriasEntity, Long> {
	
	List<MateriasEntity> findByNombreCompletoContainingIgnoreCase(String search);
	Optional<MateriasEntity> findByReferencia(String referencia);
	@Transactional
	void deleteByReferencia(String referencia);

}
