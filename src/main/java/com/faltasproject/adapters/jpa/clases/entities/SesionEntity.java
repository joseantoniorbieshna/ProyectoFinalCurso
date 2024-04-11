package com.faltasproject.adapters.jpa.clases.entities;

import java.util.Iterator;
import java.util.Set;
import java.util.stream.Collectors;

import com.faltasproject.adapters.jpa.horario.entities.HoraHorarioEntity;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.models.clases.Aula;
import com.faltasproject.domain.models.horario.Sesion;

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
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "SESIONES")
@Data
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SesionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@EqualsAndHashCode.Include
	@Column(name = "referencia",unique = true)
	private String referencia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "materia_id")
	private MateriasEntity materia;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profesor_id")
	private ProfesorEntity profesor;
	
	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(
		name = "SESION_GRUPO",
		joinColumns = @JoinColumn(name="sesion_id"),
		inverseJoinColumns = @JoinColumn(name="grupo_id")
	)
	private Set<GrupoEntity> grupos;
	@OneToMany(fetch = FetchType.EAGER,
			cascade = CascadeType.REMOVE,
			mappedBy = "sesion")
	private Set<HoraHorarioEntity> horasHorario;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "aula_id",nullable = true)
	private AulaEntity aula;

	public SesionEntity(Sesion sesion) {
		fromSesion(sesion);
	}
	public SesionEntity(String referencia, MateriasEntity materia, ProfesorEntity profesor,AulaEntity aula, Set<GrupoEntity> grupos) {
		super();
		this.referencia = referencia;
		this.materia = materia;
		this.profesor = profesor;
		this.aula=aula;
		this.grupos = grupos;
	}
	
	public void fromSesion(Sesion sesion) {
		this.referencia = sesion.getReferencia();
		
		this.materia = sesion.getMateria()!=null? new MateriasEntity(sesion.getMateria()):null;
		
		this.profesor = sesion.getProfesor()!=null? new ProfesorEntity(sesion.getProfesor()):null;
		
		this.aula = sesion.getAula()!=null? new AulaEntity(sesion.getAula()):null;
		this.grupos = sesion.getGrupos()!=null?
				sesion.getGrupos().stream()
				.map(GrupoEntity::new)
				.collect(Collectors.toSet()):null;
	}
	
	public Sesion toSesion() {
		Aula aulaSave = this.aula!=null?this.aula.toAula():null;
		
		return new Sesion(referencia,
				materia.toMateria(),
				profesor.toProfesor(),
				grupos.stream()
					.map(GrupoEntity::toGrupo)
					.collect(Collectors.toSet()),
				aulaSave
				);
	}
	
	public String getReferenciaMateria() {
		return this.materia.getReferencia();
	}
	public String getReferenciaAula() {
		return this.aula.getReferencia();
	}
	public String getReferenciaProfesor() {
		return this.profesor.getReferencia();
	}
	
	public void addGrupoEntity(GrupoEntity grupo) {
		this.grupos.add(grupo);
	}
	public boolean removeGrupoEntity(GrupoEntity grupo) {
		for (Iterator<GrupoEntity> iterator = getGrupos().iterator(); iterator.hasNext();) {
			GrupoEntity elemento = iterator.next();
		    if (elemento.equals(grupo)) {
		        iterator.remove();
		        return true;
		    }
		}
		return false;
	}
	
	@PreRemove
	private void beforeRemove() {
		this.grupos.clear();
		this.setMateria(null);
		this.setProfesor(null);
		this.setAula(null);
	}
	
	
}
