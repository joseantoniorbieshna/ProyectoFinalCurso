package com.faltasproject.domain.exceptions;

public class ConflictExceptions extends RuntimeException{
    private static final String DESCRIPTION = "Conflict Exception (409)";

    public ConflictExceptions(String details) {
        super(DESCRIPTION + ". " + details);
    }

}
