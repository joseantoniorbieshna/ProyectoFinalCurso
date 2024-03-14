package com.faltasproject.respositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faltasproject.models.entities.Materia;

public interface MateriaRepository extends JpaRepository<Materia, Long> {
	
	//@Query()
	//public Long findLastId();
	
	Optional<Materia> findByNombre(String nombre);
	
}
