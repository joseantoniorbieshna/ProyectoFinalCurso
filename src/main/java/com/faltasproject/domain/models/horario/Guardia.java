package com.faltasproject.domain.models.horario;

import com.faltasproject.domain.models.profesorado.Profesor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@ToString
@Data
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Guardia {
	@EqualsAndHashCode.Include
	private TramoHorario tramoHorario;
	@EqualsAndHashCode.Include
	private Profesor profesor;
	
	public Guardia(TramoHorario tramoHorario, Profesor profesor) {
		super();
		this.tramoHorario = tramoHorario;
		this.profesor = profesor;
	}
	
	
	public String getNombreProfesor(){
		return this.profesor.getNombre();
	}
	
	public String getReferenciaProfesor() {
		return this.profesor.getReferencia();
	}
	
	public int getDia() {
		return this.tramoHorario.getDia();
	}
	
	public int getIndice() {
		return this.tramoHorario.getIndice();
	}
}
