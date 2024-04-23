package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.horario.HoraHorario;
import com.faltasproject.domain.services.horario.HoraHorarioService;


@Controller
public class HorasHorarioControllerGraphql{

	private final HoraHorarioService horaHorarioService;

	public HorasHorarioControllerGraphql(HoraHorarioService horaHorarioService) {
		super();
		this.horaHorarioService = horaHorarioService;
	}
	
	@QueryMapping
	public List<HoraHorario> horasHorario(){
		return horaHorarioService.findAll();
	} 
	
	
	@MutationMapping
	public List<HoraHorario> getHorasHorarioByReferenciaProfesor(@Argument String referenciaProfesor){
		return horaHorarioService.findByProfesorId(referenciaProfesor);
	}
	
	
}
