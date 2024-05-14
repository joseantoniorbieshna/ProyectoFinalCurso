package com.faltasproject.domain.models.horario;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class HoraHorario {
	@EqualsAndHashCode.Include
	private Sesion sesion;
	@EqualsAndHashCode.Include
	private TramoHorario tramoHorario;
	
	public HoraHorario(Sesion sesion, TramoHorario tramoHorario) {
		super();
		this.sesion = sesion;
		this.tramoHorario = tramoHorario;
	}
	
	public int getDiaTramoHorario() {
		return tramoHorario.getDia();
	}
	
	public int getIndiceTramoHorario() {
		return tramoHorario.getIndice();
	}
	
	public String getReferenciaSesion() {
		return sesion.getReferencia();
	}
	
	public String getReferenciaProfesor() {
		return this.sesion.getReferenciaProfesor();
	}	

}
