package com.faltasproject.domain.models.horario.mappers;

import org.springframework.stereotype.Component;

import com.faltasproject.domain.models.horario.Guardia;
import com.faltasproject.domain.models.horario.dtos.IdGuardiaDTO;

@Component
public class GuardiaIdMapper {
	public IdGuardiaDTO toDto(Guardia guardia) {
		return new IdGuardiaDTO(guardia.getReferenciaProfesor(),guardia.getDia(),guardia.getIndice());
	}
}
