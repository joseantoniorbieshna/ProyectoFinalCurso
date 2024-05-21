package com.faltasproject.domain.models.horario.dtos;

import java.time.LocalDate;
import java.util.Optional;

import lombok.Data;

@Data
public class FaltaCreateInputDTO {
	private int dia;
	private int indice;
	String referenciaSesion;
	private Optional<String> comentario;
	private LocalDate fecha;
	
}
