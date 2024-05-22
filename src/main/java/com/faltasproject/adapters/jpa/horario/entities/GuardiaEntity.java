package com.faltasproject.adapters.jpa.horario.entities;

import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.models.horario.Guardia;
import com.faltasproject.domain.models.horario.TramoHorario;
import com.faltasproject.domain.models.profesorado.Profesor;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GUARDIAS", 
	uniqueConstraints = @UniqueConstraint(columnNames = {"profesor_id","tramo_horario_id_dia","tramo_horario_id_indice"}))
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GuardiaEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "profesor_id")
	private ProfesorEntity profesor;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tramo_horario_id_dia",referencedColumnName = "dia")
	@JoinColumn(name = "tramo_horario_id_indice",referencedColumnName = "indice")
	private TramoHorarioEntity tramoHorario;
	
	public GuardiaEntity(Guardia guardia) {
		fromGuardia(guardia);
	}

	public void fromGuardia(Guardia guardia) {
	 	this.setProfesor(new ProfesorEntity(guardia.getProfesor()));
	 	this.setTramoHorario(new TramoHorarioEntity(guardia.getTramoHorario()));
	}
	
	public Guardia toGuardia() {
		TramoHorario tramoHorarioModel = this.tramoHorario.toTramoHorario();
		Profesor profesorModel = this.profesor.toProfesor();
		return new Guardia(tramoHorarioModel,profesorModel);
	}
	
	public String getReferenciaProfesor() {
		return this.profesor.getReferencia();
	}

	public Integer getDia() {
		return this.tramoHorario.getDia();
	}

	public Integer getIndice() {
		return this.tramoHorario.getIndice();
	}
	
	@PreRemove
	public void beforeRemove () {
		setProfesor(null);
		setTramoHorario(null);
	}

}
