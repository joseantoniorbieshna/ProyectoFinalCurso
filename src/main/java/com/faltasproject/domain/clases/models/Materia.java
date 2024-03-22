package com.faltasproject.domain.clases.models;

import java.util.Objects;

public class Materia {
	public String id;
	public String nombreAbreviado;
	public String nombreCompleto;
	
	public Materia(String id, String nombreAbreviado, String nombreCompleto) {
		super();
		this.id = id;
		this.nombreAbreviado = nombreAbreviado;
		this.nombreCompleto = nombreCompleto;
	}
	
	public Materia( String nombreAbreviado, String nombreCompleto) {
		super();
		this.nombreAbreviado = nombreAbreviado;
		this.nombreCompleto = nombreCompleto;
	}
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getNombreAbreviado() {
		return nombreAbreviado;
	}
	public void setNombreAbreviado(String nombreAbreviado) {
		this.nombreAbreviado = nombreAbreviado;
	}
	public String getNombreCompleto() {
		return nombreCompleto;
	}
	public void setNombreCompleto(String nombreCompleto) {
		this.nombreCompleto = nombreCompleto;
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
		Materia other = (Materia) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "Materia [id=" + id + ", nombreAbreviado=" + nombreAbreviado + ", nombreCompleto=" + nombreCompleto
				+ "]";
	}
	
	
}
