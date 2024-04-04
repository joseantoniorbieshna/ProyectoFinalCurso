package com.faltasproject.adapters.jpa.clases.entities;

import java.util.Set;
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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "GRUPOS")
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
	@ManyToOne(fetch = FetchType.EAGER)
	private CursoEntity curso;
	
	@ManyToMany(mappedBy = "grupos",fetch = FetchType.EAGER)
	private Set<SesionEntity> sesiones;

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
		
		if(grupo.getCurso()!=null) {
			//No persistido
			this.curso = new CursoEntity(grupo.getReferenciaCurso());
		}
	}
	
	public Grupo toGrupo() {
		return new Grupo(getNombre(),getCurso().toCurso());
	}
	
	@PreRemove
	private void beforeRemove() {
		for(SesionEntity sesion: getSesiones()) {
			sesion.removeGrupoEntity(this);
		}
		sesiones.clear();
		
		this.setCurso(null);
	}
}
