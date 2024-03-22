package com.faltasproject.domain.persistance_ports.clases;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.clases.Materia;

@Repository
public interface MateriaPersistance {
	
	Materia create(Materia materia);
	
	Materia update(Materia materia);
	
	Stream<Materia> readAll();
	
	Materia readByCompleteName();
	
	Materia readByDni(String id);

	boolean existId(String id);

}
