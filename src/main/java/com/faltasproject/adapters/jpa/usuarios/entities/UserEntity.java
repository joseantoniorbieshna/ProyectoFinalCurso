package com.faltasproject.adapters.jpa.usuarios.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PreRemove;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
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
	
	@PreRemove
	private void beforeRemove() {
		this.setRole(null);
	}
	
}
