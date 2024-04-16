package com.faltasproject.adapters.jpa.horario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.clases.daos.SesionRepositoryJPA;
import com.faltasproject.adapters.jpa.clases.entities.SesionEntity;
import com.faltasproject.adapters.jpa.horario.daos.FaltaRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.HoraHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.daos.TramoHorarioRepositoryJPA;
import com.faltasproject.adapters.jpa.horario.entities.FaltaEntity;
import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;

@Service
public class HorarioSeederService {
	
	private final TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA;
	private final SesionRepositoryJPA sesionRepositoryJPA;
	private final HoraHorarioRepositoryJPA horaHorarioRepositoryJPA;
	private final FaltaRepositoryJPA faltaRepositoryJPA;
	
	public HorarioSeederService(TramoHorarioRepositoryJPA tramoHorarioRepositoryJPA,
			SesionRepositoryJPA sesionRepositoryJPA,
			HoraHorarioRepositoryJPA horaHorarioRepositoryJPA,
			FaltaRepositoryJPA faltaRepositoryJPA) {
		
		this.tramoHorarioRepositoryJPA = tramoHorarioRepositoryJPA;
		this.sesionRepositoryJPA=sesionRepositoryJPA;
		this.horaHorarioRepositoryJPA=horaHorarioRepositoryJPA;
		this.faltaRepositoryJPA=faltaRepositoryJPA;
	}

	public void seedDatabase() {
		Logger logger = LogManager.getLogger(this.getClass());
		
		logger.warn("----- POBLANDO BASE DE DATOS - HORARIOS -----");
		
		// TRAMOS
		TramoHorarioEntity[] tramosHorarios= {
			new TramoHorarioEntity(0, 1,  LocalTime.of(8, 0), LocalTime.of(9, 0)),
			new TramoHorarioEntity(0, 2,  LocalTime.of(9, 0), LocalTime.of(10, 0)),
			new TramoHorarioEntity(0, 3,  LocalTime.of(10, 0), LocalTime.of(11, 0)),
			new TramoHorarioEntity(0, 4,  LocalTime.of(11, 30), LocalTime.of(12, 30)),
			
			new TramoHorarioEntity(1, 1,  LocalTime.of(8, 0), LocalTime.of(9, 0)),
			new TramoHorarioEntity(1, 2,  LocalTime.of(9, 0), LocalTime.of(10, 0)),
			new TramoHorarioEntity(1, 3,  LocalTime.of(10, 0), LocalTime.of(11, 0)),
			new TramoHorarioEntity(1, 4,  LocalTime.of(11, 30), LocalTime.of(12, 30)),
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
		
		
		// FALTAS
		FaltaEntity[] faltas= {
				new FaltaEntity(horaHorarios[0], LocalDate.of(2024, 04, 5), null,"comentario profesor1"),
				new FaltaEntity(horaHorarios[1], LocalDate.of(2024, 04, 8), null,"comentario profesor2"),
				
				new FaltaEntity(horaHorarios[2], LocalDate.of(2024, 04, 2), null,"comentario profesor3"),
				new FaltaEntity(horaHorarios[3], LocalDate.of(2024, 04, 23), null,"comentario profesor4"),
		};
		
		faltaRepositoryJPA.saveAll(Arrays.asList(faltas));
	}
	
	public void deleteAll() {
		faltaRepositoryJPA.deleteAll();
		horaHorarioRepositoryJPA.deleteAll();
		tramoHorarioRepositoryJPA.deleteAll();
	}
}
