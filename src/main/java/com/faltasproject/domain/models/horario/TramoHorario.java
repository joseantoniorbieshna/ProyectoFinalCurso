package com.faltasproject.domain.models.horario;

import java.time.LocalTime;
import java.util.Objects;

public class TramoHorario {
	private Integer dia;
	private Integer indice;
	private LocalTime horaEntrada;
	private LocalTime horaSalida;
	
	

	public TramoHorario() {
		super();
	}

	public TramoHorario(Integer dia, Integer indice, LocalTime horaEntrada, LocalTime horaSalida) {
		super();
		this.dia = dia;
		this.indice = indice;
		this.horaEntrada = horaEntrada;
		this.horaSalida = horaSalida;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

	public LocalTime getHoraEntrada() {
		return horaEntrada;
	}

	public void setHoraEntrada(LocalTime horaEntrada) {
		this.horaEntrada = horaEntrada;
	}

	public LocalTime getHoraSalida() {
		return horaSalida;
	}

	public void setHoraSalida(LocalTime horaSalida) {
		this.horaSalida = horaSalida;
	}

	@Override
	public int hashCode() {
		return Objects.hash(horaSalida, dia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TramoHorario other = (TramoHorario) obj;
		return Objects.equals(horaSalida, other.horaSalida) && Objects.equals(dia, other.dia);
	}

	@Override
	public String toString() {
		return "TramoHorario [dia=" + dia + ", indice=" + indice + ", horaEntrada=" + horaEntrada + ", HoraSalida="
				+ horaSalida + "]";
	}
	
}
