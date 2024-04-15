package com.faltasproject.adapters.jpa.horario.entities;


import java.time.LocalDate;
import java.util.Optional;

import com.faltasproject.adapters.jpa.horario.entities.key_compound.FaltaKey;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.models.horario.Falta;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "FALTAS")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FaltaEntity {
	@EmbeddedId
	@EqualsAndHashCode.Include
	private FaltaKey key;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profesor_sustituto")
	private ProfesorEntity profesorSustituto;
	@Column(name = "comentario")
	private String comentario;
	
	public FaltaEntity(Falta falta) {
		fromFalta(falta);
	}
	
	public FaltaEntity(HoraHorarioEntity horaHorario,LocalDate fecha, ProfesorEntity profesorSustituto, String comentario) {
		super();
		this.key = new FaltaKey(horaHorario,fecha);
		this.profesorSustituto = profesorSustituto;
		this.comentario = comentario;
	}
	
	public LocalDate getFecha() {
		return this.key.getFecha();
	}
	public void setFecha(LocalDate fecha) {
		this.key.setFecha(fecha);
	}
	public TramoHorarioEntity getTramoHorario() {
		return this.getKey().getTramoHorario();
	}
	
	public int getDiaTramoHorario() {
		return this.getKey().getDiaTramoHorario();
	}
	public int getIndiceTramoHorario() {
		return this.getKey().getIndiceTramoHorario();
	}
	
	public String getReferenciaSesion() {
		return this.key.getReferenciaSesion();
	}
	
	public HoraHorarioEntity getHoraHorario() {
		return this.key.getHoraHorario();
	}
	
	public void fromFalta(Falta falta) {
		this.key = new FaltaKey(new HoraHorarioEntity(falta.getHoraHorario()),falta.getFecha());
		this.comentario=falta.getComentario();
		
		if(falta.getProfesorSustituto().isPresent()) {
			this.profesorSustituto=new ProfesorEntity(falta.getProfesorSustituto().get());
		}
	}
	
	public Falta toFalta() {
		Falta falta = new Falta(getHoraHorario().toHoraHorario(),getComentario(), getFecha());
		if(this.profesorSustituto!=null) {
			falta.setProfesorSustituto( Optional.of(profesorSustituto.toProfesor()) );
		}
		return falta;
	}
	
	public boolean isValidDateAndDay() {
		 return this.getFecha().getDayOfWeek().getValue()==this.getTramoHorario().getDia()+1;
	}

	public void setHoraHorario(HoraHorarioEntity horaHorario) {
		getKey().setHoraHorario(horaHorario);
	}



	
}
