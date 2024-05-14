package com.faltasproject.domain.models.horario.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class IdGuardiaDTO {
	@EqualsAndHashCode.Include
	private String referenciaProfesor;
	@EqualsAndHashCode.Include
	private int dia;
	@EqualsAndHashCode.Include
	private int indice;
}
