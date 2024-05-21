package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.services.horario.TramoHorarioService;


@Controller
public class TramoHorarioControllerGraphql{

	private final TramoHorarioService tramoHorarioService;

	public TramoHorarioControllerGraphql(TramoHorarioService tramoHorarioService) {
		super();
		this.tramoHorarioService = tramoHorarioService;
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<TramoHorario> tramosHorarios(){
		return tramoHorarioService.findAll();
	} 
	
	
}
