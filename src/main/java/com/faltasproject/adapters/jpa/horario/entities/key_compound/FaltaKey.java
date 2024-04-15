package com.faltasproject.adapters.jpa.horario.entities.key_compound;

import java.time.LocalDate;

import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;
import com.faltasproject.adapters.jpa.horario.entities.TramoHorarioEntity;

import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class FaltaKey {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hora_horario")
	HoraHorarioEntity horaHorario;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	private LocalDate fecha; 
	
	public TramoHorarioEntity getTramoHorario() {
		return this.horaHorario.getTramoHorario();
	}
	
	public int getDiaTramoHorario() {
		return this.getHoraHorario().getDiaTramoHorario();
	}
	public int getIndiceTramoHorario() {
		return this.getHoraHorario().getIndiceTramoHorario();
	}
	
	public String getReferenciaSesion() {
		return this.getHoraHorario().getReferenciaSesion();
	}
}
