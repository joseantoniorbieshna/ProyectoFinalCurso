package com.faltasproject.adapters.jpa.clases.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	private Long referencia;
	private String nombre;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "CURSO_MATERIA",
		joinColumns = @JoinColumn(name="curso_id"),
		inverseJoinColumns = @JoinColumn(name="materia_id")
	)
	private List<MateriasEntity> materias;
	
	public CursoEntity(Long referencia,String nombre, List<MateriasEntity> materias) {
		super();
		this.referencia=referencia;
		this.nombre = nombre;
		this.materias = materias;
	}
	
	public CursoEntity(Long referencia, String nombre) {
		super();
		this.referencia = referencia;
		this.nombre = nombre;
		this.materias=new ArrayList<>();
	}
	
	public CursoEntity(Curso curso){
		this.fromCurso(curso);
	}
	
	public void fromCurso(Curso curso) {
		this.referencia=curso.getReferencia();			
		this.nombre=curso.getNombre();
		this.materias = curso.getMaterias().stream()
				.map(materia -> new MateriasEntity(materia))
				.collect(Collectors.toList());			
	}
	
	public Curso toCurso() {
		Curso curso=new Curso(
				this.getReferencia(),
				this.getNombre(),
				this.getMaterias().stream()
					.map(materiaEntity->materiaEntity.toMateria())
					.collect(Collectors.toList()));
		return curso;
	}

}
