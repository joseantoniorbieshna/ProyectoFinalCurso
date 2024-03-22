package com.faltasproject.domain.persistance_ports.common;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.faltasproject.domain.models.clases.Materia;

@Repository
public interface FileTreatmentPersistance {
	
	List<Materia> readAllMaterias();
	
}
