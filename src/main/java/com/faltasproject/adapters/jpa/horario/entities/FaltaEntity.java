package com.faltasproject.adapters.jpa.horario.entities;


import com.faltasproject.adapters.jpa.horario.entities.key_compound.FaltaKey;
import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "FALTAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class FaltaEntity {
	@EmbeddedId
	@EqualsAndHashCode.Include
	private FaltaKey falta;
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profesor_sustituto")
	private ProfesorEntity profesorSustituto;
	@Column(name = "comentario")
	private String comentario;
	
}
