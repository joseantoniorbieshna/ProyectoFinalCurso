package com.faltasproject.domain.models.clases.dtos;

import java.util.Set;

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
@AllArgsConstructor
@NoArgsConstructor
public class CursoCreateDTO {
	@EqualsAndHashCode.Include
	private String referencia;
	private String nombre;
	private Set<String> referenciaMaterias;
}
