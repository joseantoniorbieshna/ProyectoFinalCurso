package com.faltasproject.domain.models.horario;

import java.util.Objects;
import java.util.Set;

import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.clases.Grupo;
import com.faltasproject.domain.models.clases.Materia;
import com.faltasproject.domain.models.profesorado.Profesor;

public class Sesion {
	private String referencia;
	private Materia materia;
	private Profesor profesor;
	private Set<Grupo> grupos;
	private Aula aula;
	public Sesion(String referencia, Materia materia, Profesor profesor, Set<Grupo> grupos, Aula aula) {
		super();
		this.referencia = referencia;
		this.materia = materia;
		this.profesor = profesor;
		this.grupos = grupos;
		this.aula=aula;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Materia getMateria() {
		return materia;
	}
	
	public String getReferenciaMateria() {
		return this.materia.getReferencia();
	}

	public void setMateria(Materia materia) {
		this.materia = materia;
	}

	public Profesor getProfesor() {
		return profesor;
	}

	public void setProfesor(Profesor profesor) {
		this.profesor = profesor;
	}
	
	public String getProfesorReferencia() {
		return this.profesor.getReferencia();
	}

	public Set<Grupo> getGrupos() {
		return grupos;
	}

	public void setGrupos(Set<Grupo> grupos) {
		this.grupos = grupos;
	}
	
	public Aula getAula() {
		return aula;
	}

	public void setAula(Aula aula) {
		this.aula = aula;
	}
	
	public String getReferenciaAula() {
		return this.aula.getReferencia();
	}
	
	public String getReferenciaProfesor() {
		return this.profesor.getReferencia();
	}

	@Override
	public int hashCode() {
		return Objects.hash(referencia);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Sesion other = (Sesion) obj;
		return Objects.equals(referencia, other.referencia);
	}

	
}
