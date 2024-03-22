package com.faltasproject.adapters.jpa.clases.entities;

import java.beans.Beans;
import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.BeanUtils;

import com.faltasproject.domain.clases.models.Materia;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MATERIAS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MateriasEntity {
	@Id
	private String id;
	private String nombreAbreviado;
	private String nombreCompleto;
	
	public MateriasEntity(Materia materia) {
		BeanUtils.copyProperties(materia, this);
		
		if(materia.getId()==null || materia.getId().isEmpty()) {
			this.id = UUID.randomUUID().toString();
		}
	}
	
	public Materia toMateria() {
		return new Materia(id, nombreAbreviado,nombreCompleto);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MateriasEntity other = (MateriasEntity) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public String toString() {
		return "MateriasEntity [id=" + id + ", nombreAbreviado=" + nombreAbreviado + ", nombreCompleto="
				+ nombreCompleto + "]";
	}
	
	
	

}
