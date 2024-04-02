package com.faltasproject.domain.services.profesorado;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.persistance_ports.profesorado.ProfesorPersistance;

@Service
public class ProfesorService {
	
	private final ProfesorPersistance profesorPersistance;
	
	public ProfesorService(ProfesorPersistance profesorPersistance) {
		this.profesorPersistance=profesorPersistance;
	}
	
	public Profesor create(Profesor profesor) {
		return profesorPersistance.create(profesor);
	}
	
}
