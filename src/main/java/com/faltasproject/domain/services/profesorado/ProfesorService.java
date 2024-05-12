package com.faltasproject.domain.services.profesorado;

import java.util.List;
import java.util.stream.Collectors;

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
	
	public List<Profesor> findAll(){
		return this.profesorPersistance.readAll().collect(Collectors.toList());
	}

	public List<Profesor> findAllWithoutRegistrationUser() {
		return this.profesorPersistance.readAll().filter(p->p.getUsuario()==null).toList();
	}
	
}
