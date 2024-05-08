package com.faltasproject.adapters.graphql;

import java.time.LocalDate;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaDeleteInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaUpdateInputDTO;
import com.faltasproject.domain.services.horario.FaltaService;

@Controller
public class FaltasControllerGraphql {

	private final FaltaService faltaService;
	
	public FaltasControllerGraphql(FaltaService faltaService) {
		this.faltaService=faltaService;
	}
	
	@QueryMapping
	public List<Falta> faltas(){
		return this.faltaService.findAll();
	}
	
	@MutationMapping
	public Falta createFalta(@Argument FaltaCreateInputDTO faltaCreateInput) {
		return this.faltaService.create(faltaCreateInput);
	}
	
	@MutationMapping
	public Falta updateFalta(@Argument FaltaUpdateInputDTO faltaUpdateInput) {
		return this.faltaService.update(faltaUpdateInput);
	}
	
	@MutationMapping
	public String deleteFalta(@Argument FaltaDeleteInputDTO faltaDeleteInput) {
		this.faltaService.delete(faltaDeleteInput);
		return "Borrado con existo";
	}
	
	@QueryMapping
	public List<Falta> getAllFaltaBetweenFechas(@Argument LocalDate fechaInicio,@Argument LocalDate fechaFin){
		return this.faltaService.findAllByFaltaBetween(fechaInicio,fechaFin);
	}
	
	

}
