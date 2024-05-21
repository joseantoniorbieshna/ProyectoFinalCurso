package com.faltasproject.domain.models.horario.dtos;

import java.time.LocalDate;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FaltaCreateByDiaProfesorDTO {
	@EqualsAndHashCode.Include
	private int dia;
	@EqualsAndHashCode.Include
	private LocalDate fecha;
	@EqualsAndHashCode.Include
	String referenciaProfesor;
	@EqualsAndHashCode.Include
	private Optional<String> comentario;
}
