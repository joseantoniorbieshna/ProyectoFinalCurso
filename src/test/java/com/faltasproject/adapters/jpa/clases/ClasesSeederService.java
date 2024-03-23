package com.faltasproject.adapters.jpa.clases;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

@Service
public class ClasesSeederService {
	private final MateriaRepositoryJPA materiaRepository;
	
	public ClasesSeederService(MateriaRepositoryJPA materiaRepository) {
		this.materiaRepository=materiaRepository;
	}
	
	public void seedDatabase() {
		Logger logger = LogManager.getLogger(this.getClass());
		
		logger.warn("\n\n\n");
		logger.warn("----- POBLANDO BASE DE DATOS CLASES -----");
		
		MateriasEntity[] materias = {
				new MateriasEntity("01","FyQ","Fisica y quimica"),
				new MateriasEntity("02","Mt","Matematicas"),
				new MateriasEntity("03","LC","Lenguaje castellano"),
				new MateriasEntity("04","HE","Historio de españa"),
				new MateriasEntity("04","Fi","Filosofia")
		};
		
		materiaRepository.saveAll(List.of(materias));
	}
	
	public void deleteAll() {
		materiaRepository.deleteAll();
	}
}
