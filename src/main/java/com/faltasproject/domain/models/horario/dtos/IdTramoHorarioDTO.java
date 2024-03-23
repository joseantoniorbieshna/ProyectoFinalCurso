package com.faltasproject.domain.models.horario.dtos;

public class IdTramoHorarioDTO {
	private Integer dia;
	private Integer indice;
	
	public IdTramoHorarioDTO() {
		super();
	}

	public IdTramoHorarioDTO(Integer dia, Integer indice) {
		super();
		this.dia = dia;
		this.indice = indice;
	}

	public Integer getDia() {
		return dia;
	}

	public void setDia(Integer dia) {
		this.dia = dia;
	}

	public Integer getIndice() {
		return indice;
	}

	public void setIndice(Integer indice) {
		this.indice = indice;
	}

}
