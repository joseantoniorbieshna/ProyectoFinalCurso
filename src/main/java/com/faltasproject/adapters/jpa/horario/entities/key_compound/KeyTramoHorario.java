package com.faltasproject.adapters.jpa.horario.entities.key_compound;

import java.io.Serializable;

import com.faltasproject.domain.models.horario.dtos.IdTramoHorarioDTO;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class KeyTramoHorario implements Serializable {
	private static final long serialVersionUID = 1L;
	@Column(name = "dia")
	private Integer dia;
	@Column(name = "indice")
	private Integer indice;
	
	public KeyTramoHorario(IdTramoHorarioDTO idTramoHorarioDTO) {
		fromIdTramoHorarioDTO(idTramoHorarioDTO);
	}
	
	public IdTramoHorarioDTO toIdTramoHorarioDTO() {
		return new IdTramoHorarioDTO(this.getDia(),this.getIndice());
	}
	
	public void fromIdTramoHorarioDTO(IdTramoHorarioDTO idTramoHorarioDTO) {
		this.setDia(idTramoHorarioDTO.getDia());
		this.setIndice(idTramoHorarioDTO.getIndice());
	}
	
}
