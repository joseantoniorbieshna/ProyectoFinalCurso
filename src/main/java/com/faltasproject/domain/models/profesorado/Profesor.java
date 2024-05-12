package com.faltasproject.domain.models.profesorado;

import com.faltasproject.domain.models.usuario.Usuario;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Profesor {
	@EqualsAndHashCode.Include
	private String referencia;
	private String nombre;
	private Usuario usuario;
	
	public Profesor(String referencia) {
		super();
		this.referencia = referencia;
		this.nombre = null;
		this.usuario=null;
	}
	
	public Profesor(String referencia, String nombre) {
		this(referencia);
		this.nombre = nombre;
		this.usuario=null;
	}
	
}
