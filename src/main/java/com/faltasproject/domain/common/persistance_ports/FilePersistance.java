package com.faltasproject.domain.common.persistance_ports;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.clases.models.Materia;

@Repository
public interface FilePersistance {
	
	List<Materia> readAllMaterias();
	
}
