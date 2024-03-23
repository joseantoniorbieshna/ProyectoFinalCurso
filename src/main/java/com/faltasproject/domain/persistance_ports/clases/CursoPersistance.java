package com.faltasproject.domain.persistance_ports.clases;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.clases.Curso;
@Repository
public interface CursoPersistance {
	
	Curso create(Curso curso);
	
	Curso update(Long id,Curso curso);
	
	Stream<Curso> readAll();
	
	Stream<Curso> readContainInCompleteName(String name);
	
	Boolean delete(Long id);
	
	Curso readById(Long id);

	Boolean existId(Long id);

}
