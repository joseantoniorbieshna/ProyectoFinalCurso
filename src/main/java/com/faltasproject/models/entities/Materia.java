package com.faltasproject.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Materias")
public class Materia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nombre;
	
	@ManyToMany
	@JoinTable(name="materias_cursos",
	joinColumns = @JoinColumn(name = "materia_id",referencedColumnName = "id"),
	inverseJoinColumns = @JoinColumn(name="curso_id",referencedColumnName = "id"))
	private List<Curso> cursos;
	
	public Materia(Long id,String nombre) {
		super();
		this.id=id;
		this.nombre = nombre;
		this.cursos = new ArrayList<>();
	}
	
	public Materia(String nombre, List<Curso> cursos) {
		super();
		this.nombre = nombre;
		this.cursos = cursos;
	}
	
	public Materia(String nombre) {
		super();
		this.nombre = nombre;
		this.cursos = new ArrayList<>();
	}
	
	public void addCurso(Curso curso) {
		this.cursos.add(curso);
	}
	
}
