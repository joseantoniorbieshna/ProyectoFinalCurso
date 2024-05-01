package com.faltasproject.adapters.jpa.clases.entities;

import java.util.HashSet;
import java.util.Set;

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
import jakarta.persistence.OneToMany;
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
	
	@OneToMany(fetch = FetchType.EAGER,mappedBy = "materia",cascade = CascadeType.REMOVE)
	private Set<SesionEntity> sesiones;
	
	public MateriasEntity(String referencia){
		super();
		this.referencia=referencia;
	}	
	public MateriasEntity(String referencia, String nombreAbreviado, String nombreCompleto) {
		this(referencia);
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
		return new Materia(String.valueOf(referencia), String.valueOf(nombreAbreviado),String.valueOf(nombreCompleto));
	}
	
	@PreRemove
	private void beforeRemove() {
		for(CursoEntity curso: cursos) {
			curso.getMaterias().remove(this);
		}
	}
}
