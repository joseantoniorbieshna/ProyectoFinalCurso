package com.faltasproject.adapters.jpa.horario.entities;

import java.util.Set;

import com.faltasproject.adapters.jpa.clases.entities.AulaEntity;
import com.faltasproject.adapters.jpa.clases.entities.GrupoEntity;
import com.faltasproject.adapters.jpa.clases.entities.MateriasEntity;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.models.clases.Aula;

import jakarta.persistence.CascadeType;
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
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class SesionEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@EqualsAndHashCode.Include
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
	@JoinColumn(name = "aula_id")
	private AulaEntity aula;

	public SesionEntity(String referencia, MateriasEntity materia, ProfesorEntity profesor,AulaEntity aula, Set<GrupoEntity> grupos) {
		super();
		this.referencia = referencia;
		this.materia = materia;
		this.profesor = profesor;
		this.aula=aula;
		this.grupos = grupos;
	}
	
	
	@PreRemove
	private void beforeRemove() {
		this.grupos.clear();
		this.setMateria(null);
		this.setProfesor(null);
		this.setAula(null);
	}
	
	
}
