package com.faltasproject.domain.persistance_ports.clases;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.horario.Sesion;

@Repository
public interface SesionPersistance {
	
	Sesion create(Sesion sesion);
	
	Sesion update(String referencia,Sesion sesion);
	
	Stream<Sesion> readAll();
	
	Sesion readByReferencia(String referencia);
	
	void delete(String referencia);
	
	boolean existReferencia(String referencia);
	
}
