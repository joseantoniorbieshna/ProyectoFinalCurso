package com.faltasproject.domain.models.horario;

import java.util.Objects;

public class HoraHorario {
	
	private String referenciaSesion;
	private String referenciaTramoHorario;
	
	public HoraHorario(String referenciaSesion, String referenciaTramoHorario) {
		super();
		this.referenciaSesion = referenciaSesion;
		this.referenciaTramoHorario = referenciaTramoHorario;
	}
	
	public String getReferenciaSesion() {
		return referenciaSesion;
	}
	public void setReferenciaSesion(String referenciaSesion) {
		this.referenciaSesion = referenciaSesion;
	}
	public String getReferenciaTramoHorario() {
		return referenciaTramoHorario;
	}
	public void setReferenciaTramoHorario(String referenciaTramoHorario) {
		this.referenciaTramoHorario = referenciaTramoHorario;
	}

	@Override
	public int hashCode() {
		return Objects.hash(referenciaSesion, referenciaTramoHorario);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HoraHorario other = (HoraHorario) obj;
		return Objects.equals(referenciaSesion, other.referenciaSesion)
				&& Objects.equals(referenciaTramoHorario, other.referenciaTramoHorario);
	}

}
