package com.faltasproject.security.usuarios.dtos;

import jakarta.validation.constraints.NotBlank;

public record ChangePasswordByUserNameDTO(@NotBlank String username,@NotBlank String actualPassword,@NotBlank String passwordToChange) {
}