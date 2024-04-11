package com.faltasproject.domain.models.clases;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Aula {
	@EqualsAndHashCode.Include
	private String referencia;
	private String nombre;

	public Aula(String referencia) {
		super();
		this.referencia=referencia;
	}

	public Aula(String referencia, String nombre) {
		this(referencia);
		this.nombre=nombre;
	}
}
