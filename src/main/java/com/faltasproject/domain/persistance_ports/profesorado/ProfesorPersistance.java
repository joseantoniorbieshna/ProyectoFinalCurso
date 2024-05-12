package com.faltasproject.domain.persistance_ports.profesorado;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.profesorado.Profesor;

@Repository
public interface ProfesorPersistance {
	
	Profesor create(Profesor profesor);
	
	Profesor update(String referencia,Profesor profesor);
	
	Stream<Profesor> readAll();
	
	Stream<Profesor> readAllNoUserAsign();
	
	boolean delete(String referencia);
	
	Profesor readByReferencia(String referencia);

	boolean existReferencia(String referencia);
}
