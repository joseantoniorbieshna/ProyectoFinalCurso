package com.faltasproject.adapters.jpa.profesorado;

import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;

@Service
public class ProfesoradoSeederService {
	
	private ProfesorRepositoryJPA profesorRepositoryJPA;

	public ProfesoradoSeederService(ProfesorRepositoryJPA profesorRepositoryJPA) {
		super();
		this.profesorRepositoryJPA = profesorRepositoryJPA;
	}

	public void seedDatabase() {
		ProfesorEntity[] profesores = {
				new ProfesorEntity("01","Jose"),
				new ProfesorEntity("02","Luis"),
				new ProfesorEntity("03","Paco"),
				new ProfesorEntity("04","Nieves"),
				new ProfesorEntity("05","Fernando"),
		};
		
		profesorRepositoryJPA.saveAll( Arrays.asList(profesores) );
	}
	
	public void deleteAll() {
		this.profesorRepositoryJPA.deleteAll();
	}
}
