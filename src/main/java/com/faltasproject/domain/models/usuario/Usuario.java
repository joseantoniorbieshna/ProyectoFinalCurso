package com.faltasproject.domain.models.usuario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {
	private String username;
	private String password;
	private RoleEnum role;
}
