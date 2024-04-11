package com.faltasproject.domain.models.clases;



import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Materia {
	@EqualsAndHashCode.Include
	private String referencia;
	private String nombreAbreviado;
	private String nombreCompleto;
	
	public Materia(String referencia) {
		super();
		this.referencia=referencia;
	}

	public Materia(String referencia, String nombreAbreviado, String nombreCompleto) {
		this(referencia);
		this.nombreAbreviado=nombreAbreviado;
		this.nombreCompleto=nombreCompleto;
	}
	
	public Materia( String nombreAbreviado, String nombreCompleto) {
		super();
		this.nombreAbreviado=nombreAbreviado;
		this.nombreCompleto=nombreCompleto;
	}
	
}
