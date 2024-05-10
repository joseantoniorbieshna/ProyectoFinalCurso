package com.faltasproject.domain.services.horario;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.faltasproject.adapters.jpa.profesorado.persistence.ProfesorPersistanceJPA;
import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.models.horario.Sesion;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaDeleteInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaUpdateInputDTO;
import com.faltasproject.domain.models.horario.dtos.IdFaltaDTO;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.models.horario.mappers.FaltaIdMapper;
import com.faltasproject.domain.models.profesorado.Profesor;
import com.faltasproject.domain.persistance_ports.clases.SesionPersistance;
import com.faltasproject.domain.persistance_ports.horario.FaltaPersistance;
import com.faltasproject.domain.persistance_ports.horario.TramoHorarioPersistance;
import com.faltasproject.domain.persistance_ports.profesorado.ProfesorPersistance;


@Service
public class FaltaService {
	
	private FaltaPersistance faltaPersistance;
	private TramoHorarioPersistance tramoPersistance;
	private SesionPersistance sesionPersistance;
	private ProfesorPersistance profesorPersistance;
	
	
	public FaltaService(FaltaPersistance faltaPersistance,
			TramoHorarioPersistance tramoPersistance,
			SesionPersistance sesionPersistance) {
		super();
		this.faltaPersistance = faltaPersistance;
		this.tramoPersistance=tramoPersistance;
		this.sesionPersistance=sesionPersistance;
	}
	
	public Falta create(FaltaCreateInputDTO faltaCreateInputDTO) {
		assertDayIsTodayOrLater(faltaCreateInputDTO.getFecha());
		TramoHorario tramoHorario = tramoPersistance.readById(new IdTramoHorarioDTO(faltaCreateInputDTO.getDia(),faltaCreateInputDTO.getIndice()));
		Sesion sesion = sesionPersistance.readByReferencia(faltaCreateInputDTO.getReferenciaSesion());
		HoraHorario horaHorario = new HoraHorario(sesion, tramoHorario);
		
		Falta falta = new Falta(horaHorario, faltaCreateInputDTO.getComentario().orElse(""), faltaCreateInputDTO.getFecha());
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

	public Falta update(FaltaUpdateInputDTO faltaUpdateInputDTO) {
		assertDayIsTodayOrLater(faltaUpdateInputDTO.getFecha());
		assertDayIsTodayOrLater(faltaUpdateInputDTO.getFechaNueva());
		
		IdFaltaDTO idFaltaActual = new IdFaltaDTO(faltaUpdateInputDTO.getReferenciaSesion(),faltaUpdateInputDTO.getDia(),faltaUpdateInputDTO.getIndice(), faltaUpdateInputDTO.getFecha());
		Falta faltaActualizar = faltaPersistance.readById(idFaltaActual);
		faltaActualizar.setComentario(faltaUpdateInputDTO.getComentario().orElse(""));
		faltaActualizar.setFecha(faltaUpdateInputDTO.getFechaNueva());
		
		return this.faltaPersistance.update(idFaltaActual,faltaActualizar);
	}
	
	private void assertDayIsTodayOrLater(LocalDate date) {
		if(date.isBefore(LocalDate.now())) {
			throw new ConflictException("La fecha es inferior al día de hoy");
		}
	}

	public void delete(IdFaltaDTO faltaDeleteInputDTO) {
		assertDayIsTodayOrLater(faltaDeleteInputDTO.getFecha());
		this.faltaPersistance.delete(faltaDeleteInputDTO);
	}

	public Falta sustituir(IdFaltaDTO faltaSustituirInput, String referenciaProfesorSustituto) {
		assertDayIsTodayOrLater(faltaSustituirInput.getFecha());
		
		Falta falta = faltaPersistance.readById(faltaSustituirInput);
		Optional<Profesor> profesor = Optional.of(profesorPersistance.readByReferencia(referenciaProfesorSustituto));
		falta.setProfesorSustituto(profesor);
		
		return this.faltaPersistance.update(faltaSustituirInput, falta);
	}


}
