package com.faltasproject.adapters.jpa.horario;

import java.sql.Time;
import java.time.LocalTime;
import java.util.Arrays;

import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPATest;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;

@Service
public class HorarioSeederService {
	
	private final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	
	public HorarioSeederService(TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA) {
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
	}

	public void seedDatabase() {
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
	}
	
	public void deleteAll() {
		tramoHorarioRepositoryJPA.deleteAll();
	}
}
