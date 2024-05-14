package com.faltasproject.domain.models.horario;

import com.faltasproject.domain.models.profesorado.Profesor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Guardia {
	@EqualsAndHashCode.Include
	private TramoHorario tamoHorario;
	@EqualsAndHashCode.Include
	private Profesor profesor;
	
	public Guardia(TramoHorario tamoHorario, Profesor profesor) {
		super();
		this.tamoHorario = tamoHorario;
		this.profesor = profesor;
	}
	
	
	public String getNombreProfesor(){
		return this.profesor.getNombre();
	}
	
	public String getReferenciaProfesor() {
		return this.profesor.getReferencia();
	}
	
	public int getDia() {
		return this.tamoHorario.getDia();
	}
	
	public int getIndice() {
		return this.tamoHorario.getIndice();
	}
}
