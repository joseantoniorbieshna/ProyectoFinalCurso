package com.faltasproject.domain.models.horario.dtos;

public class ReferenciaHoraHorarioDTO {
	private String referenciaSesion;
	private IdTramoHorarioDTO referenciaTramoHorario;
	
	public ReferenciaHoraHorarioDTO(String referenciaSesion, IdTramoHorarioDTO referenciaTramoHorario) {
		super();
		setReferenciaSesion(referenciaSesion);
		setReferenciaTramoHorario(referenciaTramoHorario);
	}

	public String getReferenciaSesion() {
		return referenciaSesion;
	}

	public void setReferenciaSesion(String referenciaSesion) {
		this.referenciaSesion = referenciaSesion;
	}

	public IdTramoHorarioDTO getReferenciaTramoHorario() {
		return referenciaTramoHorario;
	}

	public void setReferenciaTramoHorario(IdTramoHorarioDTO referenciaTramoHorario) {
		this.referenciaTramoHorario = referenciaTramoHorario;
	}

	
}
