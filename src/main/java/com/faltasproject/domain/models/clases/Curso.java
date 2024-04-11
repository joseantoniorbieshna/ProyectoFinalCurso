package com.faltasproject.domain.models.clases;

import java.util.HashSet;
import java.util.Set;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Curso {
	@EqualsAndHashCode.Include
	private String referencia;
	private String nombre;
	private Set<Materia> materias;
	
	public Curso(String referencia) {
		super();
		this.referencia=referencia;
		this.materias=new HashSet<>();
	}
	
	public Curso(String referencia, String nombre) {
		this(referencia);
		this.nombre=nombre;
	}
	
	public Curso(String nombre, Set<Materia> materias) {
		this.nombre=nombre;
		this.materias=materias;
	}
	
	public Curso(String referencia, String nombre, Set<Materia> materias) {
		this(referencia,nombre);
		this.materias=materias;
	}

}
