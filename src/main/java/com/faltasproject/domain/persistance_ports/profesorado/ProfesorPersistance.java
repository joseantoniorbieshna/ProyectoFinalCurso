package com.faltasproject.domain.persistance_ports.profesorado;

import java.util.stream.Stream;

import com.faltasproject.domain.models.clases.Curso;
import com.faltasproject.domain.models.profesorado.Profesor;

public interface ProfesorPersistance {
	
	Profesor create(Profesor profesor);
	
	Profesor update(String referencia,Profesor profesor);
	
	Stream<Profesor> readAll();
	
	boolean delete(String referencia);
	
	Curso readByReferencia(String referencia);

	boolean existReferencia(String referencia);
}
