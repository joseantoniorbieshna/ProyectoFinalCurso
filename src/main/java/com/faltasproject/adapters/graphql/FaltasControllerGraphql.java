package com.faltasproject.adapters.graphql;

import java.time.LocalDate;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaDeleteInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaUpdateInputDTO;
import com.faltasproject.domain.models.horario.dtos.IdFaltaDTO;
import com.faltasproject.domain.services.horario.FaltaService;
import com.faltasproject.security.usuarios.service.UserDetailsServiceImpl;

@Controller
public class FaltasControllerGraphql {

	private final FaltaService faltaService;
	private final UserDetailsServiceImpl userDetailsServiceImpl;
	
	public FaltasControllerGraphql(FaltaService faltaService,
			 UserDetailsServiceImpl userDetailsServiceImpl) {
		this.faltaService=faltaService;
		this.userDetailsServiceImpl=userDetailsServiceImpl;
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public List<Falta> faltas(){
		return this.faltaService.findAll();
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Falta createFalta(@Argument FaltaCreateInputDTO faltaCreateInput) {
		return this.faltaService.create(faltaCreateInput);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Falta updateFalta(@Argument FaltaUpdateInputDTO faltaUpdateInput) {
		//TODO validar que sea mi falta sea admin
		return this.faltaService.update(faltaUpdateInput);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('USER')")
	public Falta sustituirFalta(@Argument IdFaltaDTO faltaSustituirInput) {
		String referenciaProfesor= userDetailsServiceImpl.getuserInfo().referenciaProfesor();
		return this.faltaService.sustituir(faltaSustituirInput,referenciaProfesor);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String deleteFalta(@Argument IdFaltaDTO faltaDeleteInput) {
		//TODO validar que sea mi falta sea admin
		this.faltaService.delete(faltaDeleteInput);
		return "Borrado con existo";
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public List<Falta> getAllFaltaBetweenFechas(@Argument LocalDate fechaInicio,@Argument LocalDate fechaFin){
		return this.faltaService.findAllByFaltaBetween(fechaInicio,fechaFin);
	}
	

}
