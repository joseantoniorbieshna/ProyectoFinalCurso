package com.faltasproject.domain.models.horario.dtos;

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
public class ProfesorWithCountedFaltasDTO {
	@EqualsAndHashCode.Include
	private String referencia;
	private String nombre;
	private Usuario usuario;
	private int totalFaltasSustituidas;
	
	public ProfesorWithCountedFaltasDTO(String referencia,int totalFaltasSustituidas) {
		super();
		this.referencia = referencia;
		this.totalFaltasSustituidas=totalFaltasSustituidas;
		this.nombre = null;
		this.usuario=null;
	}
	
	public ProfesorWithCountedFaltasDTO(String referencia, String nombre,int totalFaltasSustituidas) {
		this(referencia,totalFaltasSustituidas);
		this.nombre = nombre;
		this.usuario=null;
	}
	
}
