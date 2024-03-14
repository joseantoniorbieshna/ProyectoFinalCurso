package com.faltasproject.models.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "Cursos")
public class Curso {
	@Id
	public Long id;
	public String nombre;
	
	@ManyToMany(mappedBy = "cursos")
	public List<Materia> materias;
	
	
	public Curso(String nombre) {
		super();
		this.nombre = nombre;
	}



	public Curso(Long id, String nombre) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.materias=new ArrayList<>();
	}
	
	
	
}
