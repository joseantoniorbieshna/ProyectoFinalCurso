package com.faltasproject.security.usuarios.dtos;

import jakarta.validation.constraints.NotBlank;

public record AuthCreateUser(@NotBlank String username,
							 @NotBlank String password,
							 @NotBlank String referenciaProfesor) {

}
