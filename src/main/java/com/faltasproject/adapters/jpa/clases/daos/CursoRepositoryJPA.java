package com.faltasproject.adapters.jpa.clases.daos;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;

public interface CursoRepositoryJPA extends JpaRepository<CursoEntity, Long> {

	List<CursoEntity> findByNombreContainingIgnoreCase(String search);
}
