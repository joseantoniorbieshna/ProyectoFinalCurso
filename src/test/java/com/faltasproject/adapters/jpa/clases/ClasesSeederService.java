package com.faltasproject.adapters.jpa.clases;


import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.daos.CursoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

@Service
public class ClasesSeederService {
	private final MateriaRepositoryJPA materiaRepository;
	private final CursoRepositoryJPA cursoRepositoryJPA;
	
	public ClasesSeederService(MateriaRepositoryJPA materiaRepository,CursoRepositoryJPA cursoRepositoryJPA) {
		this.materiaRepository=materiaRepository;
		this.cursoRepositoryJPA=cursoRepositoryJPA;
	}
	
	public void seedDatabase() {
		Logger logger = LogManager.getLogger(this.getClass());
		
		logger.warn("\n\n\n");
		logger.warn("----- POBLANDO BASE DE DATOS CLASES -----");
		
		//MATERIAS
		MateriasEntity[] materias = {
				new MateriasEntity("01","FyQ","Fisica y quimica"),
				new MateriasEntity("02","Mt","Matematicas"),
				new MateriasEntity("03","LC","Lenguaje castellano"),
				new MateriasEntity("04","HE","Historio de españa"),
				new MateriasEntity("05","Fi","Filosofia")
		};
		
		materiaRepository.saveAll(Arrays.asList(materias));
		
		
		//Curso
		CursoEntity[] cursos= {
				new CursoEntity(1L,"4º E.S.O",List.of(materias[0],materias[1],materias[2])),
				new CursoEntity(2L,"1º Bachillerato(Arte)",List.of(materias[4],materias[1])),
				new CursoEntity(3L,"3º E.S.O",List.of(materias[1])),
				new CursoEntity(4L,"1º E.S.O",List.of(materias[0],materias[1],materias[2],materias[3])),
				new CursoEntity(5L,"2º Bachillerato",List.of(materias[0],materias[1],materias[2],materias[3])),
		};
		
		cursoRepositoryJPA.saveAll(Arrays.asList(cursos));
		
	}
	
	public void deleteAll() {
		materiaRepository.deleteAll();
		cursoRepositoryJPA.deleteAll();
	}
}
