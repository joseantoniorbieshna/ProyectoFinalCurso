package com.faltasproject.adapters.jpa.clases.entities;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.faltasproject.domain.models.clases.Materia;

import jakarta.persistence.Entity;
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
	private String id;
	private String nombreAbreviado;
	private String nombreCompleto;
	
	public MateriasEntity(Materia materia) {
		fromMateria(materia);
	}
	
	public void fromMateria(Materia materia) {
		BeanUtils.copyProperties(materia, this);
		
		// Aseguro que exista un id
		if(this.getId()==null || this.getId().isEmpty()) {
			this.id = UUID.randomUUID().toString();
		}
	}
	
	public Materia toMateria() {
		return new Materia(new String(id), new String(nombreAbreviado),new String(nombreCompleto));
	}
	
}
