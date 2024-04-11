package com.faltasproject.domain.models.horario;

import java.time.LocalTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class TramoHorario {
	@EqualsAndHashCode.Include
	private Integer dia;
	@EqualsAndHashCode.Include
	private Integer indice;
	private LocalTime horaEntrada;
	private LocalTime horaSalida;

	public TramoHorario() {
		super();
	}

	public TramoHorario(Integer dia, Integer indice, LocalTime horaEntrada, LocalTime horaSalida) {
		this();
		this.dia=dia;
		this.indice=indice;
		this.horaEntrada=horaEntrada;
		this.horaSalida=horaSalida;
	}
	
}
