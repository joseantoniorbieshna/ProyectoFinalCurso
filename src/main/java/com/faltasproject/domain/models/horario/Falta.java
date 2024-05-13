package com.faltasproject.domain.models.horario;

import java.time.LocalDate;
import java.util.Optional;

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
	private Optional<Profesor> profesorSustituto;
	// DATOS EXTRAS
	private String comentario;
	private LocalDate fecha;
	
	public Falta(HoraHorario horaHorario, Profesor profesorSustituto, String comentario,
			LocalDate fecha) {
		super();
		this.horaHorario=horaHorario;
		this.profesorSustituto = Optional.of(profesorSustituto);
		this.comentario = comentario;
		this.fecha = fecha;
	}

	public Falta(HoraHorario horaHorario, String comentario, LocalDate fecha) {
		super();
		this.horaHorario = horaHorario;
		this.comentario = comentario;
		this.fecha = fecha;
		profesorSustituto = Optional.ofNullable(null);
	}
	
	
	
	public int getDiaTramoHorario() {
		return horaHorario.getDiaTramoHorario();
	}
	
	public int getIndiceTramoHorario() {
		return horaHorario.getIndiceTramoHorario();
	}
	
	public String getReferenciaSesion() {
		return horaHorario.getReferenciaSesion();
	}
	public void setProfesorSustituto(Profesor profesor) {
		this.profesorSustituto = Optional.ofNullable(profesor);
	}
	public String getReferenciaProfesorSustituto(){
		return profesorSustituto.get().getReferencia();
	}

	public String getReferenciaProfesorPropietario() {
		return horaHorario.getReferenciaProfesor();
	}
	
	
}
