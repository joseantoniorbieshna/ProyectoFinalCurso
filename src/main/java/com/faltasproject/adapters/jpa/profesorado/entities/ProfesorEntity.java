package com.faltasproject.adapters.jpa.profesorado.entities;

import org.springframework.beans.BeanUtils;

import com.faltasproject.adapters.jpa.clases.entities.CursoEntity;
import com.faltasproject.domain.models.profesorado.Profesor;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
