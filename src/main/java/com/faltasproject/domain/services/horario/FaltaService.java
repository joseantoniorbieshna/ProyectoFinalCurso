package com.faltasproject.domain.services.horario;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateInputDTO;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.persistance_ports.clases.SesionPersistance;
import com.faltasproject.domain.persistance_ports.horario.FaltaPersistance;
import com.faltasproject.domain.persistance_ports.horario.TramoHorarioPersistance;

import graphql.com.google.common.base.Optional;

@Service
public class FaltaService {
	
	private FaltaPersistance faltaPersistance;
	private TramoHorarioPersistance tramoPersistance;
	private SesionPersistance sesionPersistance;
	
	public FaltaService(FaltaPersistance faltaPersistance,
			TramoHorarioPersistance tramoPersistance,
			SesionPersistance sesionPersistance) {
		super();
		this.faltaPersistance = faltaPersistance;
		this.tramoPersistance=tramoPersistance;
		this.sesionPersistance=sesionPersistance;
	}
	
	public Falta create(FaltaCreateInputDTO faltaCreate) {
		TramoHorario tramoHorario = tramoPersistance.readById(new IdTramoHorarioDTO(faltaCreate.getDia(),faltaCreate.getIndice()));
		Sesion sesion = sesionPersistance.readByReferencia(faltaCreate.getReferenciaSesion());
		HoraHorario horaHorario = new HoraHorario(sesion, tramoHorario);
		
		Falta falta = new Falta(horaHorario, faltaCreate.getComentario().orElse(""), faltaCreate.getFecha());
		return this.faltaPersistance.create(falta);
	}

	public List<Falta> findAll() {
		return this.faltaPersistance.readAll().toList();
	}
	
	public List<Falta> findAllByFaltaBetween(LocalDate fechaInicio,LocalDate fechaFin){
		if(fechaInicio.isAfter(fechaFin)) {
			throw new ConflictException("La fecha de inicio está después de la de fin");
		}
		
		return this.faltaPersistance.readFaltasBetweenFechas(fechaInicio, fechaFin).toList();
	}


}
