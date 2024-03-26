package com.faltasproject.adapters.jpa.clases.daos;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

import jakarta.transaction.Transactional;

public interface MateriaRepositoryJPA extends JpaRepository<MateriasEntity, Long> {
	
	List<MateriasEntity> findByNombreCompletoContainingIgnoreCase(String search);
	Optional<MateriasEntity> findByReferencia(String referencia);
	void deleteByReferencia(String referencia);
    
	@Modifying
    @Transactional
    @Query(value = "DELETE FROM CURSO_MATERIA WHERE MATERIA_ID IN (SELECT ID FROM MATERIAS WHERE REFERENCIA = :materiaReferencia)", nativeQuery = true)
    void deleteAllRelationFromMateriaByReferencia(String materiaReferencia);
    
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM CURSO_MATERIA", nativeQuery = true)
    void deleteAllRelationFromCursoMateria();

}
