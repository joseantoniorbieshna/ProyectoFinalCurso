package com.faltasproject.domain.models.horario;

import java.time.LocalDate;
import java.util.Objects;

import com.faltasproject.domain.models.profesorado.Profesor;

public class Falta {
	// RELACIONES
	private Sesion sesion;
	private TramoHorario tramoHorario;
	private Profesor profesorSustituto;
	// DATOS EXTRAS
	private String comentario;
	private LocalDate fecha;
	
	public Falta(Sesion sesion, TramoHorario tramoHorario, Profesor profesorSustituto, String comentario,
			LocalDate fecha) {
		super();
		this.sesion = sesion;
		this.tramoHorario = tramoHorario;
		this.profesorSustituto = profesorSustituto;
		this.comentario = comentario;
		this.fecha = fecha;
	}

	public Sesion getSesion() {
		return sesion;
	}

	public void setSesion(Sesion sesion) {
		this.sesion = sesion;
	}

	public TramoHorario getTramoHorario() {
		return tramoHorario;
	}

	public void setTramoHorario(TramoHorario tramoHorario) {
		this.tramoHorario = tramoHorario;
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

	@Override
	public int hashCode() {
		return Objects.hash(fecha, sesion);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Falta other = (Falta) obj;
		return Objects.equals(fecha, other.fecha) && Objects.equals(sesion, other.sesion);
	}
	
}
