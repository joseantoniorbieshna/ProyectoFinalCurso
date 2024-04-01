package com.faltasproject.adapters.jpa.clases;


import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.daos.AulaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.CursoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.AulaEntity;
import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;

@Service
public class ClasesSeederService {
	private final MateriaRepositoryJPA materiaRepository;
	private final CursoRepositoryJPA cursoRepositoryJPA;
	private final AulaRepositoryJPA aulaRepositoryJPA;
	private final GrupoRepositoryJPA grupoRepositoryJPA;
	
	public ClasesSeederService(MateriaRepositoryJPA materiaRepository,
			CursoRepositoryJPA cursoRepositoryJPA,
			AulaRepositoryJPA aulaRepositoryJPA,
			 GrupoRepositoryJPA grupoRepositoryJPA) {
		this.materiaRepository=materiaRepository;
		this.cursoRepositoryJPA=cursoRepositoryJPA;
		this.aulaRepositoryJPA=aulaRepositoryJPA;
		this.grupoRepositoryJPA=grupoRepositoryJPA;
	}
	
	public void seedDatabase() {
		Logger logger = LogManager.getLogger(this.getClass());
		
		logger.warn("----- POBLANDO BASE DE DATOS - CLASES -----");
		
		// MATERIAS
		MateriasEntity[] materias = {
				new MateriasEntity("01","FyQ","Fisica y quimica"),
				new MateriasEntity("02","Mt","Matematicas"),
				new MateriasEntity("03","LC","Lenguaje castellano"),
				new MateriasEntity("04","HE","Historio de españa"),
				new MateriasEntity("05","Fi","Filosofia")
		};
		
		materiaRepository.saveAll(Arrays.asList(materias));
		
		
		// CURSO
		CursoEntity[] cursos= {
				new CursoEntity("1","4º E.S.O", Sets.set( materias[0],materias[1],materias[2])),
				new CursoEntity("2","1º Bachillerato(Arte)", Sets.set(materias[4],materias[1])),
				new CursoEntity("3","3º E.S.O", Sets.set(materias[1])),
				new CursoEntity("4","1º E.S.O", Sets.set(materias[0],materias[1],materias[2],materias[3])),
				new CursoEntity("5","2º Bachillerato", Sets.set(materias[0],materias[1],materias[2],materias[3])),
		};
		
		cursoRepositoryJPA.saveAll(Arrays.asList(cursos));
		
		// AULA
		AulaEntity[] aula={
			new AulaEntity("1","AULA A"),
			new AulaEntity("2","AULA B"),
			new AulaEntity("3","AULA C"),
			new AulaEntity("4","AULA D"),
			new AulaEntity("5","AULA E"),
		};
		
		aulaRepositoryJPA.saveAll(Arrays.asList(aula));
		
		// GRUPO
		GrupoEntity[] grupos= {
				new GrupoEntity("E4D",cursos[4]),
				
				new GrupoEntity("1A",cursos[1]),
				new GrupoEntity("1B",cursos[1]),
				new GrupoEntity("1C",cursos[1]),
				
				new GrupoEntity("4A",cursos[0]),
				new GrupoEntity("4B",cursos[0]),
				new GrupoEntity("4C",cursos[0]),
				
		};
		grupoRepositoryJPA.saveAll(Arrays.asList(grupos));
	}
	
	public void deleteAll() {
		materiaRepository.deleteAll();
		grupoRepositoryJPA.deleteAll();
		cursoRepositoryJPA.deleteAll();
		aulaRepositoryJPA.deleteAll();
	}
}
