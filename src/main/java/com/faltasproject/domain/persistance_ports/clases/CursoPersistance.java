package com.faltasproject.domain.persistance_ports.clases;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.clases.Curso;
@Repository
public interface CursoPersistance {
	
	Curso create(Curso curso);
	
	Curso update(String referencia,Curso curso);
	
	Stream<Curso> readAll();
	
	Stream<Curso> readContainInCompleteName(String name);
	
	boolean delete(String referencia);
	
	Curso readByReferencia(String referencia);

	boolean existReferencia(String referencia);

}
