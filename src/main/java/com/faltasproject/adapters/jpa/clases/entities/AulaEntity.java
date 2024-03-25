package com.faltasproject.adapters.jpa.clases.entities;

import com.faltasproject.domain.models.clases.Aula;
import jakarta.persistence.Entity;
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
	@EqualsAndHashCode.Include
	@Id
	private Long id;
	private String nombre;
	
	public AulaEntity(Aula aula) {
		fromAula(aula);
	}
	
	public void fromAula(Aula aula) {
		if(this.id==null) {
			this.id=aula.getId();			
		}
		this.nombre=aula.getNombre();
	}
	
	public Aula toAula() {
		Aula aula=new Aula(
				this.getId(),
				this.getNombre());
		return aula;
	}
	
}
