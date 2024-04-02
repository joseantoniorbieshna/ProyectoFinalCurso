package com.faltasproject.adapters.jpa.horario;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.adapters.jpa.horario.daos.SesionRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.SesionEntity;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;

@Service
public class HorarioSeederService {
	
	private final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	private final SesionRepositoryJPA sesionRepositoryJPA;
	
	private final ProfesorRepositoryJPA profesorRepositoryJPA;
	private final MateriaRepositoryJPA materiaRepositoryJPA;
	private final GrupoRepositoryJPA grupoRepositoryJPA;
	
	
	public HorarioSeederService(TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA,
			SesionRepositoryJPA sesionRepositoryJPA,
			ProfesorRepositoryJPA profesorRepositoryJPA,
			MateriaRepositoryJPA materiaRepositoryJPA,
			GrupoRepositoryJPA grupoRepositoryJPA) {
		
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
		this.sesionRepositoryJPA=sesionRepositoryJPA;
		
		this.profesorRepositoryJPA=profesorRepositoryJPA;
		this.materiaRepositoryJPA=materiaRepositoryJPA;
		this.grupoRepositoryJPA=grupoRepositoryJPA;
	}

	public void seedDatabase() {
		Logger logger = LogManager.getLogger(this.getClass());
		
		logger.warn("----- POBLANDO BASE DE DATOS - HORARIOS -----");
		
		// TRAMOS
		TramoHorarioEntity[] tramosHorarios= {
			new TramoHorarioEntity(0, 1, Time.valueOf( LocalTime.of(8, 0) ),Time.valueOf( LocalTime.of(9, 0))),
			new TramoHorarioEntity(0, 2, Time.valueOf( LocalTime.of(9, 0) ),Time.valueOf( LocalTime.of(10, 0))),
			new TramoHorarioEntity(0, 3, Time.valueOf( LocalTime.of(10, 0) ),Time.valueOf( LocalTime.of(11, 0))),
			new TramoHorarioEntity(0, 4, Time.valueOf( LocalTime.of(11, 30) ),Time.valueOf( LocalTime.of(12, 30))),
			
			new TramoHorarioEntity(1, 1, Time.valueOf( LocalTime.of(8, 0) ),Time.valueOf( LocalTime.of(9, 0))),
			new TramoHorarioEntity(1, 2, Time.valueOf( LocalTime.of(9, 0) ),Time.valueOf( LocalTime.of(10, 0))),
			new TramoHorarioEntity(1, 3, Time.valueOf( LocalTime.of(10, 0) ),Time.valueOf( LocalTime.of(11, 0))),
			new TramoHorarioEntity(1, 4, Time.valueOf( LocalTime.of(11, 30) ),Time.valueOf( LocalTime.of(12, 30))),
		};
		
		tramoHorarioRepositoryJPA.saveAll( Arrays.asList(tramosHorarios) );
		
		
		// SESIONES
		MateriasEntity[] materias = {
				materiaRepositoryJPA.findByReferencia("01").get(),
				materiaRepositoryJPA.findByReferencia("02").get(),
				materiaRepositoryJPA.findByReferencia("03").get(),
		};
		
		ProfesorEntity[] profesores = {
				profesorRepositoryJPA.findByReferencia("01").get(),
				profesorRepositoryJPA.findByReferencia("02").get(),
				profesorRepositoryJPA.findByReferencia("03").get()
		};
		
		GrupoEntity[] grupos = {
				grupoRepositoryJPA.findByNombreEquals("1A").get(),
				grupoRepositoryJPA.findByNombreEquals("1B").get(),
				grupoRepositoryJPA.findByNombreEquals("1C").get()
		};
		
		SesionEntity[] sesiones= {
				new SesionEntity( "01",materias[0],profesores[0],Sets.set(grupos[0],grupos[1],grupos[2])),
				new SesionEntity( "02",materias[1],profesores[1],Sets.set(grupos[1],grupos[2])),
				new SesionEntity( "02",materias[2],profesores[2],Sets.set(grupos[0],grupos[1])),
				
		};
		sesionRepositoryJPA.saveAll(Arrays.asList(sesiones));
		
	}
	
	public void deleteAll() {
		tramoHorarioRepositoryJPA.deleteAll();
		sesionRepositoryJPA.deleteAll();
	}
}
