package com.faltasproject.adapters.jpa.clases.entities;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import com.faltasproject.domain.models.clases.Curso;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="CURSOS")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CursoEntity {
	@Id
	private Long id;
	private String nombre;
	
	@ManyToMany()
	@JoinTable(
		name = "CURSO_MATERIA",
		joinColumns = @JoinColumn(name="curso_id"),
		inverseJoinColumns = @JoinColumn(name="materia_id")
	)
	private List<MateriasEntity> materias;
	
	public CursoEntity(String nombre, List<MateriasEntity> materias) {
		super();
		this.nombre = nombre;
		this.materias = materias;
	}
	public CursoEntity(Curso curso){
		this.fromCurso(curso);
	}
	
	public void fromCurso(Curso curso) {
		this.id=curso.getId();
		this.nombre=curso.getNombre();
		this.materias = curso.getMaterias().stream()
				.map(materia -> new MateriasEntity(materia))
				.collect(Collectors.toList());
	}
	
	public Curso toCurso() {
		return new Curso(
				this.getId(),
				this.getNombre(),
				this.getMaterias().stream()
					.map(materiaEntity->materiaEntity.toMateria())
					.collect(Collectors.toList())
				);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CursoEntity other = (CursoEntity) obj;
		return Objects.equals(id, other.id);
	}
	
	@Override
	public String toString() {
		return "CursoEntity [id=" + id + ", nombre=" + nombre + ", materias=" + materias + "]";
	}
	
	
}
