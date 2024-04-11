package com.faltasproject.adapters.jpa.horario;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.util.Sets;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.daos.AulaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.GrupoRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.MateriaRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.daos.SesionRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.SesionEntity;
import com.faltasproject.adapters.jpa.horario.daos.HoraHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;
import com.faltasproject.adapters.jpa.profesorado.daos.ProfesorRepositoryJPA;

@Service
public class HorarioSeederService {
	
	private final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	private final SesionRepositoryJPA sesionRepositoryJPA;
	private final HoraHorarioRepositoryJPA horaHorarioRepositoryJPA;
	
	public HorarioSeederService(TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA,
			SesionRepositoryJPA sesionRepositoryJPA,
			HoraHorarioRepositoryJPA horaHorarioRepositoryJPA) {
		
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
		this.sesionRepositoryJPA=sesionRepositoryJPA;
		this.horaHorarioRepositoryJPA=horaHorarioRepositoryJPA;
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
		
		
		
		SesionEntity[] sesiones = {
				sesionRepositoryJPA.findByReferencia("01").get(),
				sesionRepositoryJPA.findByReferencia("02").get(),
				sesionRepositoryJPA.findByReferencia("03").get(),
		};
		
		HoraHorarioEntity[] horaHorarios= {
				new HoraHorarioEntity(tramosHorarios[2],sesiones[2]),
				new HoraHorarioEntity(tramosHorarios[1],sesiones[2]),
				new HoraHorarioEntity(tramosHorarios[5],sesiones[2]),

				new HoraHorarioEntity(tramosHorarios[5],sesiones[1]),
				new HoraHorarioEntity(tramosHorarios[3],sesiones[1]),
				new HoraHorarioEntity(tramosHorarios[2],sesiones[1]),
				
				new HoraHorarioEntity(tramosHorarios[0],sesiones[0]),
				new HoraHorarioEntity(tramosHorarios[6],sesiones[0]),
				new HoraHorarioEntity(tramosHorarios[5],sesiones[0]),
				
		};
		
		horaHorarioRepositoryJPA.saveAll( Arrays.asList(horaHorarios));
		
	}
	
	public void deleteAll() {
		horaHorarioRepositoryJPA.deleteAll();
		tramoHorarioRepositoryJPA.deleteAll();
	}
}
