package com.faltasproject.domain.models.clases;

import java.util.Objects;

public class Materia {
	private String referencia;
	private String nombreAbreviado;
	private String nombreCompleto;
	
	public Materia(String referencia) {
		super();
		setReferencia(referencia);
	}

	public Materia(String referencia, String nombreAbreviado, String nombreCompleto) {
		this(referencia);
		setNombreAbreviado(nombreAbreviado);
		setNombreCompleto(nombreCompleto);
	}
	
	public Materia( String nombreAbreviado, String nombreCompleto) {
		super();
		setNombreAbreviado(nombreAbreviado);
		setNombreCompleto(nombreCompleto);
	}
	
	public String getReferencia() {
		return referencia;
	}
	public void setReferencia(String referencia) {
		this.referencia = referencia;
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
		Materia other = (Materia) obj;
		return Objects.equals(referencia, other.referencia);
	}

	@Override
	public String toString() {
		return "Materia [referencia=" + referencia + ", nombreAbreviado=" + nombreAbreviado + ", nombreCompleto=" + nombreCompleto
				+ "]";
	}
	
}
