package com.faltasproject.domain.exceptions;

public class IlegalArgumentException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String DESCRIPTION = "Conflict Exception (400)";

	public IlegalArgumentException(String details) {
		super(DESCRIPTION + ". " + details);
	}

}
