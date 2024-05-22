package com.faltasproject.security.usuarios.entity;


import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;
import com.faltasproject.domain.models.usuario.RoleEnum;
import com.faltasproject.domain.models.usuario.Usuario;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="USUARIOS")
public class UsuarioEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(unique = true)
	private String username;
	private String password;
	@Column(name="is_enabled")
	private boolean isEnabled;
	@Column(name="account_No_Expired")
	private boolean accountNoExpired;
	@Column(name="account_No_Locked")
	private boolean accountNoLocked;
	@Column(name="credential_No_Expired")
	private boolean credentialNoExpired;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id",referencedColumnName = "id")
	private RoleEntity role;
	
    @OneToOne(mappedBy = "usuario",fetch = FetchType.LAZY)
    private ProfesorEntity profesor;
	
	
	@PreRemove
	private void beforeRemove() {
		this.setRole(null);
		this.profesor.setUsuario(null);
	}
	
	public String getStringRole() {
		return this.getRole().getRoleEnum().name();
	}

	public UsuarioEntity(String username, String password, boolean isEnabled, boolean accountNoExpired,
			boolean accountNoLocked, boolean credentialNoExpired, RoleEntity role) {
		super();
		this.username = username;
		this.password = password;
		this.isEnabled = isEnabled;
		this.accountNoExpired = accountNoExpired;
		this.accountNoLocked = accountNoLocked;
		this.credentialNoExpired = credentialNoExpired;
		this.role = role;
	}
	
	
	public void fromUsuario(Usuario usuario) {
		setUsername(usuario.getUsername());
		setPassword(usuario.getPassword());
		RoleEnum roleEnum = null;
		if(usuario.getRole()!=null) {
			setRole(new RoleEntity(usuario.getRole()));
		}
	}
	
	public Usuario toUsuario(){
		RoleEnum roleEnum = null;
		if(role!=null) {
			roleEnum = role.getRoleEnum();
		}
		return Usuario
				.builder()
				.username(username)
				.password(password)
				.role(roleEnum)
				.build();
	}

	public UsuarioEntity(Usuario usuario) {
		fromUsuario(usuario);
	}
	
	
	
	
}
