package com.faltasproject.adapters.jpa.horario.entities;

import java.sql.Time;
import java.util.Set;

import com.faltasproject.adapters.jpa.horario.entities.key_compound.TramoHorarioKey;
import com.faltasproject.domain.models.horario.TramoHorario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
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
	private TramoHorarioKey key;
	@Column(name = "hora_entrada")
	@Temporal(TemporalType.TIME)
	private Time horaEntrada;
	@Column(name = "hora_salida")
	@Temporal(TemporalType.TIME)
	private Time horaSalida;
	
	@OneToMany(fetch = FetchType.EAGER, mappedBy = "tramoHorario",cascade = CascadeType.REMOVE)
	private Set<HoraHorarioEntity> horaHorarioEntity;
	
	public TramoHorarioEntity(TramoHorario tramoHorario) {
		fromTramoHorario(tramoHorario);
	}
	
	public TramoHorarioEntity(Integer dia,Integer indice,Time horaEntrada,Time horaSalida) {
		this.key = new TramoHorarioKey(dia,indice);
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
			this.setKey( new TramoHorarioKey(tramohorario.getDia(), tramohorario.getIndice()) );			
		}
		if(tramohorario.getHoraEntrada()!=null) {
			this.setHoraEntrada( Time.valueOf(tramohorario.getHoraEntrada()) );
		}
		if(tramohorario.getHoraSalida()!=null) {
			this.setHoraSalida( Time.valueOf(tramohorario.getHoraSalida()) );
		}
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
