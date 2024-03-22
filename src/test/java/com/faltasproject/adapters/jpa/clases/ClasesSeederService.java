package com.faltasproject.adapters.jpa.clases;

import java.util.List;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.daos.MateriaRepository;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

@Service
public class ClasesSeederService {
	private final MateriaRepository materiaRepository;
	
	public ClasesSeederService(MateriaRepository materiaRepository) {
		this.materiaRepository=materiaRepository;
	}
	
	public void seedDatabase() {
		MateriasEntity[] materias = {
				new MateriasEntity("01","FyQ","Fisica y quimica"),
				new MateriasEntity("02","Mt","Matematicas"),
				new MateriasEntity("03","LC","Lenguaje castellano"),
				new MateriasEntity("04","HE","Historio de españa")
		};
		
		materiaRepository.saveAll(List.of(materias));
	}
	
	public void deleteAll() {
		materiaRepository.deleteAll();
	}
}
