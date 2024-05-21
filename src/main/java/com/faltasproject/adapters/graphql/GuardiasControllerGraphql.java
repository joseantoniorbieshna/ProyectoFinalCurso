package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.horario.Guardia;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.services.horario.GuardiaService;

@Controller
public class GuardiasControllerGraphql {
	
	private final GuardiaService guardiaService;
	
	public GuardiasControllerGraphql(GuardiaService guardiaService) {
		super();
		this.guardiaService = guardiaService;
	}

	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<Guardia> guardias() {
		return this.guardiaService.findAll();
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<Guardia> gaurdiasByProfesor(@Argument String referenciaProfesor) {
		return this.guardiaService.findAllByReferenciaProfesor(referenciaProfesor);
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public List<Guardia> guardiasByDiaAndIndice(@Argument IdTramoHorarioDTO tramoHorario ) {
		return this.guardiaService.findAllByDiaAndIndice(tramoHorario);
	}

}
