package com.faltasproject.adapters.graphql;

import java.time.LocalDate;
import java.util.List;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;

import com.faltasproject.domain.exceptions.ConflictException;
import com.faltasproject.domain.models.horario.Falta;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateByDiaProfesorDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaCreateInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaSustituirInputDTO;
import com.faltasproject.domain.models.horario.dtos.FaltaUpdateInputDTO;
import com.faltasproject.domain.models.horario.dtos.IdFaltaDTO;
import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.domain.services.horario.FaltaService;
import com.faltasproject.security.usuarios.dtos.UserInfo;
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
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public List<Falta> faltasByDiaAndIndice(@Argument int dia, @Argument int indice){
		return this.faltaService.findAllTodayAndLaterByIdAndIndice(dia,indice);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Falta createFalta(@Argument FaltaCreateInputDTO faltaCreateInput) {
		UserInfo userInfoDTO = userDetailsServiceImpl.getuserInfo();
		return this.faltaService.create(faltaCreateInput,userInfoDTO);
	}
	@MutationMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public List<Falta> createFullDayFalta(@Argument FaltaCreateByDiaProfesorDTO faltaCreateAllInput) {
		UserInfo userInfoDTO = userDetailsServiceImpl.getuserInfo();
		return this.faltaService.createAll(faltaCreateAllInput,userInfoDTO);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public Falta updateFalta(@Argument FaltaUpdateInputDTO faltaUpdateInput) {
		UserInfo userInfoDTO = userDetailsServiceImpl.getuserInfo();
		return this.faltaService.update(faltaUpdateInput,userInfoDTO);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public Falta sustituirFalta(@Argument FaltaSustituirInputDTO faltaSustituirInput) {
		
		userDetailsServiceImpl.assertForUserAndIsTheSameReferenciaProfesor(faltaSustituirInput.getReferenciaProfesorSustituto());
		
		return this.faltaService.sustituir(faltaSustituirInput);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('USER','ADMIN')")
	public Falta cancelarFalta(@Argument IdFaltaDTO faltaCancelarInput) {
		UserInfo userInfo = userDetailsServiceImpl.getuserInfo();
		return this.faltaService.cancelar(faltaCancelarInput,userInfo);
	}
	
	@MutationMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public String deleteFalta(@Argument IdFaltaDTO faltaDeleteInput) {
		UserInfo userInfoDTO = userDetailsServiceImpl.getuserInfo();
		this.faltaService.delete(faltaDeleteInput,userInfoDTO);
		return "Borrado con existo";
	}
	
	@QueryMapping
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public List<Falta> getAllFaltaBetweenFechas(@Argument LocalDate fechaInicio,@Argument LocalDate fechaFin){
		return this.faltaService.findAllByFaltaBetween(fechaInicio,fechaFin);
	}
	

}
