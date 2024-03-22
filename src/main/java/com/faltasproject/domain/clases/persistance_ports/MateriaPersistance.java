package com.faltasproject.domain.clases.persistance_ports;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.clases.models.Materia;

@Repository
public interface MateriaPersistance {
	
	Materia create(Materia materia);
	
	Materia update(Materia materia);
	
	Stream<Materia> readAll();
	
	Materia readByName();

}
