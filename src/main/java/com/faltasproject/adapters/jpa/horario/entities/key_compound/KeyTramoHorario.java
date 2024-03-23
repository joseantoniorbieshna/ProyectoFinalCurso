package com.faltasproject.adapters.jpa.horario.entities.key_compound;

import java.io.Serializable;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class KeyTramoHorario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "dia")
	private Integer dia;
	@Column(name = "indice")
	private Integer indice;
	
	
}
