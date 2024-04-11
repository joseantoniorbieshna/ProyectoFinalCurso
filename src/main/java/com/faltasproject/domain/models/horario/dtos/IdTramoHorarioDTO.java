package com.faltasproject.domain.models.horario.dtos;

import com.faltasproject.domain.models.horario.TramoHorario;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class IdTramoHorarioDTO {
	@EqualsAndHashCode.Include
	private Integer dia;
	@EqualsAndHashCode.Include
	private Integer indice;
	
	public IdTramoHorarioDTO() {
		super();
	}
	
	public IdTramoHorarioDTO(Integer dia, Integer indice) {
		super();
		this.dia = dia;
		this.indice = indice;
	}

	public IdTramoHorarioDTO(TramoHorario tramoHorario) {
		this.dia = tramoHorario.getDia();
		this.indice = tramoHorario.getIndice();
	}
}
