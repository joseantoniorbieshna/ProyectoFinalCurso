package com.faltasproject.security.usuarios.entity;

import java.util.Set;

import com.faltasproject.domain.models.usuario.RoleEnum;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name="ROLES")
@NoArgsConstructor
public class RoleEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="role_name")
	@Enumerated(EnumType.STRING)
	private RoleEnum roleEnum;
	
    @OneToMany(mappedBy = "role")
    private Set<UsuarioEntity> usuarios;
    
	@PreRemove
	private void beforeRemove() {
		for(UsuarioEntity usuario: usuarios) {
			usuarios.remove(usuario);
		}
	}

	public RoleEntity(RoleEnum roleEnum) {
		super();
		this.roleEnum = roleEnum;
	}
	
}
