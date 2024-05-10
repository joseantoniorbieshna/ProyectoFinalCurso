package com.faltasproject.security.usuarios.entity;

import java.util.Optional;

import com.faltasproject.adapters.jpa.profesorado.entities.ProfesorEntity;

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
import jakarta.persistence.PrimaryKeyJoinColumn;
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
@Table(name="USERS")
public class UserEntity {
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
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "role_id",referencedColumnName = "id")
	private RoleEntity role;
	
	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "profesor_id",unique = true)
	private ProfesorEntity profesor;
	
	
	@PreRemove
	private void beforeRemove() {
		this.setProfesor(null);
		this.setRole(null);
	}
	
	public String getStringRole() {
		return this.getRole().getRoleEnum().name();
	}

	public UserEntity(String username, String password, boolean isEnabled, boolean accountNoExpired,
			boolean accountNoLocked, boolean credentialNoExpired, RoleEntity role) {
		super();
		this.username = username;
		this.password = password;
		this.isEnabled = isEnabled;
		this.accountNoExpired = accountNoExpired;
		this.accountNoLocked = accountNoLocked;
		this.credentialNoExpired = credentialNoExpired;
		this.role = role;
		this.setProfesor(null);
	}

	public UserEntity(String username, String password, boolean isEnabled, boolean accountNoExpired,
			boolean accountNoLocked, boolean credentialNoExpired, RoleEntity role, ProfesorEntity profesor) {
		super();
		this.username = username;
		this.password = password;
		this.isEnabled = isEnabled;
		this.accountNoExpired = accountNoExpired;
		this.accountNoLocked = accountNoLocked;
		this.credentialNoExpired = credentialNoExpired;
		this.role = role;
		this.profesor = profesor;
	}
	
	
	
	
}
