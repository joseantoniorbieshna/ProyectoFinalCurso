package com.faltasproject.domain.models.clases;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Curso {
	private String referencia;
	private String nombre;
	private Set<Materia> materias;
	
	public Curso(String referencia) {
		super();
		setReferencia(referencia);
		setMaterias(new HashSet<>());
	}
	
	public Curso(String referencia, String nombre) {
		this(referencia);
		setNombre(nombre);
	}
	
	public Curso(String nombre, Set<Materia> materias) {
		setNombre(nombre);
		setMaterias(materias);
	}
	
	public Curso(String referencia, String nombre, Set<Materia> materias) {
		this(referencia,nombre);
		setMaterias(materias);
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(Set<Materia> materias) {
		this.materias = materias;
	}

	@Override
	public int hashCode() {
		return Objects.hash(referencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Curso other = (Curso) obj;
		return Objects.equals(referencia, other.referencia);
	}

	@Override
	public String toString() {
		return "Curso [referencia=" + referencia + ", nombre=" + nombre + "]";
	}

}
