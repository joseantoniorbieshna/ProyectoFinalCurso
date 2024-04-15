package com.faltasproject.domain.models.horario.dtos;

import java.time.LocalDate;

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
public class IdFaltaDTO {
	@EqualsAndHashCode.Include
	private String referenciaSesion;
	@EqualsAndHashCode.Include
	private int dia;
	@EqualsAndHashCode.Include
	private int indice;
	@EqualsAndHashCode.Include
	private LocalDate fecha;

}
