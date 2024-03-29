package com.faltasproject.domain.persistance_ports.clases;

import java.util.stream.Stream;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.clases.Grupo;

@Repository
public interface GrupoPersistance {
	
	Grupo create(Grupo grupo);
	
	Grupo update(String nombre,Grupo grupo);
	
	Stream<Grupo> readAll();
	
	Stream<Grupo> readContainInName(String search);
	
	boolean delete(String nombre);
	
	Grupo readByNombre(String nombre);

	boolean existNombre(String nombre);
}
