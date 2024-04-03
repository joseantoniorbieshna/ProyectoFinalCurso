package com.faltasproject.domain.models.clases;

import java.util.Objects;

public class Aula {
	private String referencia;
	private String nombre;

	public Aula(String referencia) {
		super();
		setReferencia(referencia);
	}

	public Aula(String referencia, String nombre) {
		this(referencia);
		setNombre(nombre);
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
		Aula other = (Aula) obj;
		return Objects.equals(referencia, other.referencia);
	}

	@Override
	public String toString() {
		return "Aula [referencia=" + referencia + ", nombre=" + nombre + "]";
	}
}
