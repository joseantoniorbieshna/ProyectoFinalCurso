package com.faltasproject.domain.models.horario.dtos;

import com.faltasproject.domain.models.horario.TramoHorario;

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
public class GuardiaCountedFaltaProfesorDTO {
	@EqualsAndHashCode.Include
	private TramoHorario tramoHorario;
	@EqualsAndHashCode.Include
	private ProfesorWithCountedFaltasDTO profesor;
	
	public GuardiaCountedFaltaProfesorDTO(TramoHorario tramoHorario, ProfesorWithCountedFaltasDTO profesor) {
		super();
		this.tramoHorario = tramoHorario;
		this.profesor=profesor;
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
