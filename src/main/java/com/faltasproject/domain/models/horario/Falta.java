package com.faltasproject.domain.models.horario;

import java.time.LocalDate;

import com.faltasproject.domain.models.profesorado.Profesor;

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

	public Profesor getProfesorSustituto() {
		return profesorSustituto;
	}

	public void setProfesorSustituto(Profesor profesorSustituto) {
		this.profesorSustituto = profesorSustituto;
	}

	public String getComentario() {
		return comentario;
	}

	public void setComentario(String comentario) {
		this.comentario = comentario;
	}

	public LocalDate getFecha() {
		return fecha;
	}

	public void setFecha(LocalDate fecha) {
		this.fecha = fecha;
	}

	public HoraHorario getHoraHorario() {
		return horaHorario;
	}

	public void setHoraHorario(HoraHorario horaHorario) {
		this.horaHorario = horaHorario;
	}
	
}
