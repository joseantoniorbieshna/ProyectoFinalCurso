package com.faltasproject.adapters.jpa.clases.entities;

import com.faltasproject.domain.models.clases.Aula;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
	private Long referencia;
	private String nombre;
		
	public AulaEntity(Long referencia, String nombre) {
		super();
		this.referencia = referencia;
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

}
