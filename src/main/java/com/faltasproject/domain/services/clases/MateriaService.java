package com.faltasproject.domain.services.clases;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.persistance_ports.clases.MateriaPersistance;

@Service
public class MateriaService {
	
	private final MateriaPersistance materiaPersistance;
	
	public MateriaService(MateriaPersistance materiaPersistance) {
		this.materiaPersistance=materiaPersistance;
	}
	
	public Materia create(Materia materia) {
		this.assertIdMateriaNotExist(materia.getId());
		return materiaPersistance.create(materia);
	}
	
	public void assertIdMateriaNotExist(String id) {
		if(this.materiaPersistance.existId(id)) {
			throw new ConflictExceptions("ya existe el id: "+id); 
		}
	}

}
