package com.faltasproject.domain.models.clases;

import java.util.Objects;

public class Aula {
	private Long id;
	private String nombre;
	
	public Aula() {
		super();
	}

	public Aula(String nombre) {
		super();
		this.nombre = nombre;
	}

	public Aula(Long id, String nombre) {
		super();
		this.id = id;
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
		Aula other = (Aula) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Aula [id=" + id + ", nombre=" + nombre + "]";
	}
}
