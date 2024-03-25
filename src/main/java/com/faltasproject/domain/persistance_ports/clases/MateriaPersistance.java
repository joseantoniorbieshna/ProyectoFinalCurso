package com.faltasproject.domain.persistance_ports.clases;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.clases.Materia;

@Repository
public interface MateriaPersistance {
	
	Materia create(Materia materia);
	
	Materia update(String referencia,Materia materia);
	
	Stream<Materia> readAll();
	
	Stream<Materia> readContainInCompleteName(String name);
	
	Boolean delete(String referencia);
	
	Materia readByReferencia(String referencia);

	Boolean existReferencia(String referencia);

}
