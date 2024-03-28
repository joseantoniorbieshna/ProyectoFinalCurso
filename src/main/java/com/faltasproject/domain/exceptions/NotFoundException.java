package com.faltasproject.domain.exceptions;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private static final String DESCRIPTION = "Not found exception (404)";

	public NotFoundException(String details) {
		super(DESCRIPTION + ". " + details);
	}
}
