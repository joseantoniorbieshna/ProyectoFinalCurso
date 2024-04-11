package com.faltasproject.domain.models.horario;

import java.util.Objects;

public class HoraHorario {
	
	private Sesion sesion;
	private TramoHorario tramoHorario;
	
	public HoraHorario(Sesion sesion, TramoHorario tramoHorario) {
		super();
		this.sesion = sesion;
		this.tramoHorario = tramoHorario;
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
	
	public int getDiaTramoHorario() {
		return tramoHorario.getDia();
	}
	
	public int getIndiceTramoHorario() {
		return tramoHorario.getIndice();
	}
	
	public String getReferenciaSesion() {
		return sesion.getReferencia();
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(sesion, tramoHorario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoraHorario other = (HoraHorario) obj;
		return Objects.equals(sesion, other.sesion) && Objects.equals(tramoHorario, other.tramoHorario);
	}

	@Override
	public String toString() {
		return "HoraHorario [sesion=" + sesion + ", tramoHorario=" + tramoHorario + "]";
	}
	
}
