package com.faltasproject.adapters.jpa.clases.entities;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.faltasproject.domain.models.clases.Materia;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MATERIAS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MateriasEntity {
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@EqualsAndHashCode.Include
	private String referencia;
	private String nombreAbreviado;
	private String nombreCompleto;
	
	public MateriasEntity(String referencia, String nombreAbreviado, String nombreCompleto) {
		super();
		this.referencia = referencia;
		this.nombreAbreviado = nombreAbreviado;
		this.nombreCompleto = nombreCompleto;
	}
	
	public MateriasEntity(Materia materia) {
		fromMateria(materia);
	}
	
	public void fromMateria(Materia materia) {
		BeanUtils.copyProperties(materia, this);
	}
	
	public Materia toMateria() {
		return new Materia(new String(referencia), new String(nombreAbreviado),new String(nombreCompleto));
	}

	
}
