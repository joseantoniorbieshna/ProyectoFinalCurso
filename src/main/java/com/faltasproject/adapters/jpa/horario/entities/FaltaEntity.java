package com.faltasproject.adapters.jpa.horario.entities;


import java.time.LocalDate;
import java.util.Optional;

import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.models.horario.Falta;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "FALTAS", uniqueConstraints = {@UniqueConstraint(columnNames = {"horaHorario","fecha"})})
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FaltaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "hora_horario")
	@EqualsAndHashCode.Include
	HoraHorarioEntity horaHorario;
	@Temporal(TemporalType.DATE)
	@Column(name = "fecha")
	@EqualsAndHashCode.Include
	private LocalDate fecha;
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
		this.horaHorario=horaHorario;
		this.fecha=fecha;
		this.profesorSustituto = profesorSustituto;
		this.comentario = comentario;
	}
	
	public TramoHorarioEntity getTramoHorario() {
		return this.horaHorario.getTramoHorario();
	}
	
	public int getDiaTramoHorario() {
		return this.horaHorario.getDiaTramoHorario();
	}
	public int getIndiceTramoHorario() {
		return this.horaHorario.getIndiceTramoHorario();
	}
	
	public String getReferenciaSesion() {
		return this.horaHorario.getReferenciaSesion();
	}
	
	public void fromFalta(Falta falta) {
		this.horaHorario=new HoraHorarioEntity(falta.getHoraHorario());
		this.fecha=falta.getFecha();
		this.comentario=falta.getComentario();
		
		if(falta.getProfesorSustituto().isPresent()) {
			this.profesorSustituto=new ProfesorEntity(falta.getProfesorSustituto().get());
		}
	}
	
	public Falta toFalta() {
		Falta falta = new Falta(getHoraHorario().toHoraHorario(),getComentario(), getFecha());
		if(this.profesorSustituto!=null) {
			falta.setProfesorSustituto(profesorSustituto.toProfesor());
		}
		return falta;
	}
	
	public boolean isValidDateAndDay() {
		 return this.getFecha().getDayOfWeek().getValue()==this.getTramoHorario().getDia()+1;
	}



	
}
