package com.faltasproject.adapters.jpa.profesorado.entities;

import java.util.Set;

import org.springframework.beans.BeanUtils;

import com.faltasproject.adapters.jpa.horario.entities.SesionEntity;
import com.faltasproject.domain.models.profesorado.Profesor;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PROFESORES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class ProfesorEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@EqualsAndHashCode.Include
	@Column(unique = true)
	private String referencia;
	private String nombre;
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "profesor",cascade = CascadeType.REMOVE)
	private Set<SesionEntity> materia;
	
	public ProfesorEntity(Profesor profesor) {
		fromProfesor(profesor);
	}
	
	public ProfesorEntity(String referencia, String nombre) {
		super();
		this.referencia = referencia;
		this.nombre = nombre;
	}
	
	public void fromProfesor(Profesor profesor) {
		BeanUtils.copyProperties(profesor, this);
	}
	
	public Profesor toProfesor() {
		return new Profesor(this.referencia,this.nombre);
	}

	
	
}
