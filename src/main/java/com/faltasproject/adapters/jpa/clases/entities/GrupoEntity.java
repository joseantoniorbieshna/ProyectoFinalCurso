package com.faltasproject.adapters.jpa.clases.entities;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.hibernate.Hibernate;
import org.springframework.beans.BeanUtils;
import com.faltasproject.domain.models.clases.Grupo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GRUPOS")
@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
	@EqualsAndHashCode(onlyExplicitlyIncluded = true)
	public class GrupoEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@Column(unique = true)
	@EqualsAndHashCode.Include
	private String nombre;
	@ManyToOne(fetch = FetchType.LAZY)
	private CursoEntity curso;
	
	@ManyToMany(mappedBy = "grupos",fetch = FetchType.LAZY)
	private Set<SesionEntity> sesiones;

	public GrupoEntity(Grupo grupo) {
		super();
		fromGrupo(grupo);
		this.sesiones = new HashSet<>();
	}
	
	public GrupoEntity(String nombre, CursoEntity curso) {
		super();
		this.nombre = nombre;
		this.curso = curso;
		this.sesiones = new HashSet<>();
	}
	
	public void fromGrupo(Grupo grupo) {
		if(grupo.getNombre()!=null) {
			setNombre(grupo.getNombre());			
		}
	}
	
	public Grupo toGrupo() {
		return new Grupo(getNombre(),getCurso().toCurso());
	}
	
	@PreRemove
	private void beforeRemove() {
		Hibernate.initialize(sesiones);
	    if (sesiones != null) {
	        Iterator<SesionEntity> iterator = sesiones.iterator();
	        while (iterator.hasNext()) {
	            SesionEntity sesion = iterator.next();
	            iterator.remove();
	            sesion.getGrupos().remove(this);
	        }
	    }
		
		this.setCurso(null);
	}
}
