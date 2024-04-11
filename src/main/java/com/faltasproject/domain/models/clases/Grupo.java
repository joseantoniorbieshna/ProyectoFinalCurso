package com.faltasproject.domain.models.clases;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Grupo {
	@EqualsAndHashCode.Include
	private String nombre;
	private Curso curso;
	
	public Grupo(String nombre) {
		super();
		this.nombre=nombre;
	}
	
	public Grupo(String nombre, Curso curso) {
		this(nombre);
		this.curso=curso;
	}
	
	public String getReferenciaCurso() {
		return getCurso().getReferencia();
	}


	
}
