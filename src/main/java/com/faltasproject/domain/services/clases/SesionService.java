package com.faltasproject.domain.services.clases;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.persistance_ports.clases.SesionPersistance;

@Service
public class SesionService {
	
	private final SesionPersistance sesionPersistance;

	public SesionService(SesionPersistance sesionPersistance) {
		super();
		this.sesionPersistance = sesionPersistance;
	}
	

	public void create(Sesion sesion) {
		sesionPersistance.create(sesion);
	}
	

}
