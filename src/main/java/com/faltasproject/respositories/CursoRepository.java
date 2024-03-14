package com.faltasproject.respositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.faltasproject.models.entities.Curso;

public interface CursoRepository extends JpaRepository<Curso, Long> {
	
	//@Query()
	//public Long findLastId();

}
