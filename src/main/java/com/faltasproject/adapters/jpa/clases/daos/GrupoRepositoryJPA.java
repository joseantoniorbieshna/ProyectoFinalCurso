package com.faltasproject.adapters.jpa.clases.daos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import java.util.Optional;
import java.util.List;



public interface GrupoRepositoryJPA extends JpaRepository<GrupoEntity, Long>{
	Optional<GrupoEntity> findByNombreEquals(String nombre);
	List<GrupoEntity> findByNombreContainingIgnoreCase(String search);
	void deleteByNombre(String nombre);
}
