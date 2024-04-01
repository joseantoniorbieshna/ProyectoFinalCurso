package com.faltasproject.adapters.jpa.clases.entities;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


import com.faltasproject.domain.models.clases.Curso;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name="CURSOS")
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class CursoEntity {
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@EqualsAndHashCode.Include
	@Column(unique = true)
	private String referencia;
	private String nombre;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "CURSO_MATERIA",
		joinColumns = @JoinColumn(name="curso_id"),
		inverseJoinColumns = @JoinColumn(name="materia_id")
	)
	private Set<MateriasEntity> materias;
	
	@OneToMany(mappedBy = "curso",fetch = FetchType.EAGER,cascade = CascadeType.REMOVE)
	private Set<GrupoEntity> grupos;
	
	public CursoEntity(String referencia,String nombre, Set<MateriasEntity> materias) {
		super();
		this.referencia=referencia;
		this.nombre = nombre;
		this.materias = materias;
	}
	
	public CursoEntity(String referencia) {
		super();
		this.referencia = referencia;
		this.materias=new HashSet<>();
	}
	
	public CursoEntity(String referencia, String nombre) {
		this(referencia);
		this.nombre = nombre;
	}
	
	public CursoEntity(Curso curso){
		this.fromCurso(curso);
	}
	
	public void fromCurso(Curso curso) {
		this.referencia=curso.getReferencia();			
		this.nombre=curso.getNombre();
		this.materias = curso.getMaterias().stream()
				.map(MateriasEntity::new)
				.collect(Collectors.toSet());			
	}
	
	public Curso toCurso() {
		return new Curso(
				this.getReferencia(),
				this.getNombre(),
				this.getMaterias().stream()
					.map(materiaEntity->materiaEntity.toMateria())
					.collect(Collectors.toSet()));
	}
	
	@PreRemove
	public void beforeRemove () {
		this.materias.clear();
	}

}
