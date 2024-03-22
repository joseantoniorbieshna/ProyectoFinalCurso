package com.faltasproject.adapters.rest.exceptions_handlers;

import lombok.Getter;

@Getter
public class ErrorMessage {
	private final String error;
	private final String message;
	
	public ErrorMessage(Exception exception) {
		this.error=exception.getClass().getSimpleName();
		this.message=exception.getMessage();
	}

	@Override
	public String toString() {
		return "ErrorMessage [error=" + error + ", message=" + message + "]";
	}
	
}
