package com.faltasproject.domain.models.profesorado;

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

	public String getPreferencia() {
		return referencia;
	}

	public void setPreferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	
	
}
