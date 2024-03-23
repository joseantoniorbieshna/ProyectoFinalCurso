package com.faltasproject.adapters.rest.exceptions_handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.faltasproject.domain.exceptions.ConflictExceptions;
import com.faltasproject.domain.exceptions.NotFoundException;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class ExceptionsHandler {
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler({ConflictExceptions.class})
	@ResponseBody
	public ErrorMessage conflict(Exception excepction) {
		log.error(excepction.toString());
		return new ErrorMessage(excepction);
	}
	
	
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler({NotFoundException.class})
	@ResponseBody
	public ErrorMessage notFound(Exception excepction) {
		log.error(excepction.toString());
		return new ErrorMessage(excepction);
	}
}
