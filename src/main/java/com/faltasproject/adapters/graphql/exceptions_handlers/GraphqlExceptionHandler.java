package com.faltasproject.adapters.graphql.exceptions_handlers;

import org.springframework.graphql.data.method.annotation.GraphQlExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

import graphql.GraphQLError;

@ControllerAdvice
public class GraphqlExceptionHandler {
	@GraphQlExceptionHandler
	public GraphQLError handlerException(Exception excepction) {
		return GraphQLError.newError()
				.message(excepction.getMessage())
				.build();
	}
}
