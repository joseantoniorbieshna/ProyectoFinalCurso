package com.faltasproject.domain.models.clases;

import java.util.Objects;

public class Grupo {
	private String nombre;
	private Curso curso;
	
	public Grupo(String nombre) {
		super();
		setNombre(nombre);
	}
	
	public Grupo(String nombre, Curso curso) {
		this(nombre);
		setCurso(curso);
	}
	
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	public Curso getCurso() {
		return curso;
	}
	
	public String getReferenciaCurso() {
		return getCurso().getReferencia();
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	@Override
	public int hashCode() {
		return Objects.hash(nombre);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grupo other = (Grupo) obj;
		return Objects.equals(nombre, other.nombre);
	}

	@Override
	public String toString() {
		return "Grupo [nombre=" + nombre + ", curso=" + curso + "]";
	}
	
}
