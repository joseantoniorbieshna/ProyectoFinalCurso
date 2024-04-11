package com.faltasproject.adapters.jpa.horario.entities;

import com.faltasproject.adapters.jpa.clases.entities.SesionEntity;
import com.faltasproject.domain.models.horario.HoraHorario;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinColumns;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "HORA_HORARIO", 
	uniqueConstraints = @UniqueConstraint(columnNames = {"sesion_id","tramo_horario_id_dia","tramo_horario_id_indice"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HoraHorarioEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "sesion_id")
	private SesionEntity sesion;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "tramo_horario_id_dia",referencedColumnName = "dia")
	@JoinColumn(name = "tramo_horario_id_indice",referencedColumnName = "indice")
	private TramoHorarioEntity tramoHorario;

	public HoraHorarioEntity(TramoHorarioEntity tramoHorarioEntity, SesionEntity sesionEntity) {
		this.tramoHorario=tramoHorarioEntity;
		this.sesion=sesionEntity;
	}
	
	@PreRemove
	public void beforeRemove () {
		setSesion(null);
		setTramoHorario(null);
	}

	public HoraHorarioEntity(HoraHorario horaHorario) {
		super();
		fromHoraHorario(horaHorario);
	}
	
	public void fromHoraHorario(HoraHorario horaHorario) {
		this.sesion = new SesionEntity(horaHorario.getSesion());
		this.tramoHorario = new TramoHorarioEntity(horaHorario.getTramoHorario());
	}
	public HoraHorario toHoraHorario() {
		return new HoraHorario(sesion.toSesion(), tramoHorario.toTramoHorario());
	}

}
