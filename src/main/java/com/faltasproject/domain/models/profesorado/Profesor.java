package com.faltasproject.domain.models.profesorado;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Profesor {
	@EqualsAndHashCode.Include
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
	
}
