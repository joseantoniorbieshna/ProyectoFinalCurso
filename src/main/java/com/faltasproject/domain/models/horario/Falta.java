package com.faltasproject.domain.models.horario;

import java.time.LocalDate;

import com.faltasproject.domain.models.profesorado.Profesor;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Getter
@Setter
public class Falta {
	// RELACIONES
	private HoraHorario horaHorario;
	private Profesor profesorSustituto;
	// DATOS EXTRAS
	private String comentario;
	private LocalDate fecha;
	
	public Falta(HoraHorario horaHorario, Profesor profesorSustituto, String comentario,
			LocalDate fecha) {
		super();
		this.horaHorario=horaHorario;
		this.profesorSustituto = profesorSustituto;
		this.comentario = comentario;
		this.fecha = fecha;
	}
	
}
