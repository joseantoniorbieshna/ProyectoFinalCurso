package com.faltasproject.adapters.graphql;

import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.horario.Guardia;
import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;
import com.faltasproject.domain.services.horario.GuardiaService;
import com.faltasproject.security.usuarios.service.UserDetailsServiceImpl;

@Controller
public class GuardiasControllerGraphql {
	
	private final GuardiaService guardiaService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	public GuardiasControllerGraphql(GuardiaService guardiaService, UserDetailsServiceImpl userDetailsServiceImpl) {
		super();
		this.guardiaService = guardiaService;
		this.userDetailsServiceImpl = userDetailsServiceImpl;
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
