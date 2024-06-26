package com.faltasproject.domain.persistance_ports.clases;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.clases.Aula;


@Repository
public interface AulaPersistance {
	
	Aula create(Aula aula);
	
	Aula update(String referencia,Aula aula);
	
	Stream<Aula> readAll();
	
	Stream<Aula> readContainInName(String name);
	
	boolean delete(String referencia);
	
	Aula readByReferencia(String referencia);

	boolean existReferencia(String referencia); 
}
