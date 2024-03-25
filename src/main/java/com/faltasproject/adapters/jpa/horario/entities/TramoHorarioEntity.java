package com.faltasproject.adapters.jpa.horario.entities;

import java.sql.Time;

import com.faltasproject.adapters.jpa.horario.entities.key_compound.KeyTramoHorario;
import com.faltasproject.domain.models.horario.TramoHorario;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "TRAMO_HORARIO")
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TramoHorarioEntity {
	@EmbeddedId
	@EqualsAndHashCode.Include
	KeyTramoHorario key;
	@Column(name = "hora_entrada")
	@Temporal(TemporalType.TIME)
	private Time horaEntrada;
	@Column(name = "hora_salida")
		@Temporal(TemporalType.TIME)
		private Time horaSalida;
	
	public TramoHorarioEntity(TramoHorario tramoHorario) {
		fromTramoHorario(tramoHorario);
	}
	
	public TramoHorarioEntity(Integer dia,Integer indice,Time horaEntrada,Time horaSalida) {
		this.key = new KeyTramoHorario(dia,indice);
		setHoraEntrada(horaEntrada);
		setHoraSalida(horaSalida);
	}
	
	public TramoHorario toTramoHorario() {
		TramoHorario tramohorario = new TramoHorario();
		tramohorario.setDia(getDia());
		tramohorario.setIndice(getIndice());
		tramohorario.setHoraEntrada(this.horaEntrada.toLocalTime());
		tramohorario.setHoraSalida(this.horaSalida.toLocalTime());
		return tramohorario;
	}
	
	public void fromTramoHorario(TramoHorario tramohorario) {
		if(key==null) {
			this.setKey( new KeyTramoHorario(tramohorario.getDia(), tramohorario.getIndice()) );			
		}
		this.setHoraEntrada( Time.valueOf(tramohorario.getHoraEntrada()) );
		this.setHoraSalida( Time.valueOf(tramohorario.getHoraSalida()) );
	}
	
	
	public void setDia(Integer dia) {
		this.key.setDia(dia);
	}
	
	public Integer getDia() {
		return this.key.getDia();
	}
	
	public void setIndicie(Integer indice) {
		this.key.setIndice(indice);
	}
	
	public Integer getIndice() {
		return this.key.getIndice();
	}
	
}
