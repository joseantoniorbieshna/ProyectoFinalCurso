package com.faltasproject.domain.models.horario.mappers;

import org.springframework.stereotype.Component;

import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.dtos.IdFaltaDTO;

@Component
public class FaltaIdMapper{
	
	public IdFaltaDTO toDto(Falta falta) {
		return new IdFaltaDTO(falta.getReferenciaSesion(),falta.getDiaTramoHorario(),falta.getIndiceTramoHorario(),falta.getFecha());
	}

}
