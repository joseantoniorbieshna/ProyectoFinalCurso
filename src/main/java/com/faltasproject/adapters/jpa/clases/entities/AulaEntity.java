package com.faltasproject.adapters.jpa.clases.entities;

import java.util.Set;

import com.faltasproject.domain.models.clases.Aula;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "AULAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)

public class AulaEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@EqualsAndHashCode.Include
	private Long id;
	@Column(unique = true)
	private String referencia;
	private String nombre;
	
	@OneToMany(fetch = FetchType.LAZY,mappedBy = "aula",cascade = CascadeType.REMOVE)
	private Set<SesionEntity> sesiones;
		
	public AulaEntity(String referencia) {
		super();
		this.referencia = referencia;
	}
	
	public AulaEntity(String referencia, String nombre) {
		this(referencia);
		this.nombre = nombre;
	}
	
	public AulaEntity(Aula aula) {
		fromAula(aula);
	}
	
	public void fromAula(Aula aula) {
		this.nombre=aula.getNombre();
		this.referencia=aula.getReferencia();			
	}
	
	public Aula toAula() {
		return new Aula(
				this.getReferencia(),
				this.getNombre());
	}
	
	@PreRemove
	private void beforeRemove() {
		if(getSesiones()!=null) {
			for(SesionEntity sesion: getSesiones()) {
				sesion.setAula(null);
			}			
		}
	}

}
