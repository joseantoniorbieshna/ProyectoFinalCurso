package com.faltasproject.domain.services.clases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.persistance_ports.clases.MateriaPersistance;

@Service
public class MateriaService {
	
	private final MateriaPersistance materiaPersistance;
	
	public MateriaService(MateriaPersistance materiaPersistance) {
		this.materiaPersistance=materiaPersistance;
	}
	
	public Materia create(Materia materia) {
		return materiaPersistance.create(materia);
	}
	
	public List<Materia> findAll(){
		return materiaPersistance.readAll().toList();
	}

	public Materia findMateriaByReferencia(String referencia) {
		return this.materiaPersistance.readByReferencia(referencia);
	}


}
