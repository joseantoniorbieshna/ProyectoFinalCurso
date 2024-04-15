package com.faltasproject.domain.models.horario.dtos;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class IdHoraHorarioDTO {
	@EqualsAndHashCode.Include
	private String referenciaSesion;
	@EqualsAndHashCode.Include
	private int dia;
	@EqualsAndHashCode.Include
	private int indice;

}
