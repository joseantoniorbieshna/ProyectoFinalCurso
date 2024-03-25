package com.faltasproject.domain.models.clases;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Curso {
	private Long referencia;
	private String nombre;
	private List<Materia> materias;
	
	public Curso() {
		super();
	}

	public Curso(Long referencia, String nombre, List<Materia> materias) {
		super();
		this.referencia = referencia;
		this.nombre = nombre;
		this.materias = materias;
	}
	

	public Curso(Long referencia, String nombre) {
		super();
		this.referencia = referencia;
		this.nombre = nombre;
		this.materias=new ArrayList<>();
	}
	
	public Curso(String nombre) {
		super();
		this.nombre = nombre;
		this.materias=new ArrayList<>();
	}


	public Long getReferencia() {
		return referencia;
	}

	public void setReferencia(Long referencia) {
		this.referencia = referencia;
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
