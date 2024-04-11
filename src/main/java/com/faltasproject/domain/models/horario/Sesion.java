package com.faltasproject.domain.models.horario;

import java.util.Set;

import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.profesorado.Profesor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Sesion {
	@EqualsAndHashCode.Include
	private String referencia;
	private Materia materia;
	private Profesor profesor;
	private Set<Grupo> grupos;
	private Aula aula;
	public Sesion(String referencia, Materia materia, Profesor profesor, Set<Grupo> grupos, Aula aula) {
		this(referencia);
		this.materia = materia;
		this.profesor = profesor;
		this.grupos = grupos;
		this.aula=aula;
	}

	public Sesion(String referencia) {
		super();
		this.referencia=referencia;
	}

	
	public String getReferenciaMateria() {
		return this.materia.getReferencia();
	}
	
	public String getProfesorReferencia() {
		return this.profesor.getReferencia();
	}
	
	public String getReferenciaAula() {
		return this.aula.getReferencia();
	}
	
	public String getReferenciaProfesor() {
		return this.profesor.getReferencia();
	}

	
}
