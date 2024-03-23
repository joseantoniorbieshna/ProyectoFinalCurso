package com.faltasproject.domain.models.clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {
	private Long id;
	private String nombre;
	private List<Materia> materias;

	public Curso(Long id, String nombre, List<Materia> materias) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.materias = materias;
	}
	

	public Curso(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.materias=new ArrayList<>();
	}
	
	public Curso(String nombre) {
		super();
		this.nombre = nombre;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Materia> getMaterias() {
		return materias;
	}

	public void setMaterias(List<Materia> materias) {
		this.materias = materias;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
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
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Curso [id=" + id + ", nombre=" + nombre + "]";
	}

}
