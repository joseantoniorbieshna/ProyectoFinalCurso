package com.faltasproject.domain.models.profesorado;

import java.util.Objects;

public class Profesor {
	private String referencia;
	private String nombre;
	
	public Profesor(String referencia) {
		super();
		this.referencia = referencia;
	}
	
	public Profesor(String referencia, String nombre) {
		this(referencia);
		this.nombre = nombre;
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
		Profesor other = (Profesor) obj;
		return Objects.equals(referencia, other.referencia);
	}

	@Override
	public String toString() {
		return "Profesor [referencia=" + referencia + ", nombre=" + nombre + "]";
	}
	
}
