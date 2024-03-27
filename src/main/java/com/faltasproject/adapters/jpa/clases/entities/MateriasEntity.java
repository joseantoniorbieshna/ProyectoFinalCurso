package com.faltasproject.adapters.jpa.clases.entities;

import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.beans.BeanUtils;

import com.faltasproject.domain.models.clases.Materia;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

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
	
	@ManyToMany(mappedBy = "materias",fetch = FetchType.EAGER)
	private Set<CursoEntity> cursos;
	
	public MateriasEntity(String referencia, String nombreAbreviado, String nombreCompleto) {
		super();
		this.referencia = referencia;
		this.nombreAbreviado = nombreAbreviado;
		this.nombreCompleto = nombreCompleto;
		this.cursos=new HashSet<>();
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
	
	@PreRemove
	private void beforeRemove() {
		for(CursoEntity curso: cursos) {
			curso.getMaterias().remove(this);
		}
	}
}
