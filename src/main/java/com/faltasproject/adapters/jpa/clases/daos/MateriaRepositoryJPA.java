package com.faltasproject.adapters.jpa.clases.daos;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

public interface MateriaRepositoryJPA extends JpaRepository<MateriasEntity, String> {
	
	List<MateriasEntity> findByNombreCompletoContainingIgnoreCase(String search);
	

}
