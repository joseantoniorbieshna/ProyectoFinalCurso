package com.faltasproject.domain.dto;


import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
@AllArgsConstructor
public class InputHoraHorarioDTO {
	@EqualsAndHashCode.Include
	private String referenciaSesion;
	@EqualsAndHashCode.Include
	private int dia;
	@EqualsAndHashCode.Include
	private int indice;
}
