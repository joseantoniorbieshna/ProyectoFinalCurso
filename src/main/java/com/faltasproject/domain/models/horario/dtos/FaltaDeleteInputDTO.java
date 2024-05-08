package com.faltasproject.domain.models.horario.dtos;

import java.time.LocalDate;

import lombok.Data;

@Data
public class FaltaDeleteInputDTO {
	private int dia;
	private int indice;
	String referenciaSesion;
	private LocalDate fecha;
}
