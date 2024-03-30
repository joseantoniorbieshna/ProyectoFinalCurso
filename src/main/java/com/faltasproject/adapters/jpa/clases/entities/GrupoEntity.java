package com.faltasproject.adapters.jpa.clases.entities;


import org.springframework.beans.BeanUtils;

import com.faltasproject.domain.models.clases.Grupo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "GRUPOS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GrupoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	@EqualsAndHashCode.Include
	private String nombre;
	@ManyToOne(fetch = FetchType.EAGER)
	private CursoEntity curso;

	public GrupoEntity(Grupo grupo) {
		super();
		fromGrupo(grupo);
	}
	
	public GrupoEntity(String nombre, CursoEntity curso) {
		super();
		this.nombre = nombre;
		this.curso = curso;
	}
	
	public void fromGrupo(Grupo grupo) {
		BeanUtils.copyProperties(grupo, this);
		//No persistido
		this.curso = new CursoEntity(grupo.getReferenciaCurso());
	}
	
	public Grupo toGrupo() {
		return new Grupo(getNombre(),getCurso().toCurso());
	}

}
