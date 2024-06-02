package com.faltasproject.domain.models.horario.mappers;

import org.springframework.stereotype.Component;

import com.faltasproject.domain.models.horario.dtos.ProfesorWithCountedFaltasDTO;
import com.faltasproject.domain.models.profesorado.Profesor;
@Component
public class ProfesorWithCountMapper {
	
	public ProfesorWithCountedFaltasDTO mapToDTO(Profesor profesor,int totalFaltaSustituidas) {
		return new ProfesorWithCountedFaltasDTO(profesor.getReferencia(),profesor.getNombre(),profesor.getUsuario(),totalFaltaSustituidas);	
	}

}
