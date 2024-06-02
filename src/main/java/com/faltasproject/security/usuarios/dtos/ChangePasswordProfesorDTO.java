package com.faltasproject.security.usuarios.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordProfesorDTO(@NotBlank String referenciaProfesor,@NotBlank String password) {

}
