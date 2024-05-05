package com.faltasproject;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.faltasproject.security.usuarios.daos.RoleRepositoryJPA;
import com.faltasproject.security.usuarios.daos.UserRepositoryJPA;
import com.faltasproject.security.usuarios.entity.RoleEntity;
import com.faltasproject.security.usuarios.entity.RoleEnum;
import com.faltasproject.security.usuarios.entity.UserEntity;

@SpringBootApplication
public class FaltasprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaltasprojectApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(RoleRepositoryJPA roleRepositoryJPA, UserRepositoryJPA userRepositoryJPA) {
		return args->{
			
			/*ROLES*/
			Optional<RoleEntity> roleAdmin = roleRepositoryJPA.findByRoleEnum(RoleEnum.ADMIN);
			if(!roleAdmin.isPresent()) {
				roleAdmin = Optional.of( roleRepositoryJPA.save(new RoleEntity(RoleEnum.ADMIN)) );
			}
			
			Optional<RoleEntity> roleUser = roleRepositoryJPA.findByRoleEnum(RoleEnum.USER);
			if(!roleUser.isPresent()) {
				roleUser = Optional.of( roleRepositoryJPA.save(new RoleEntity(RoleEnum.USER)) );
			}
			
			/*USUARIO*/
			Optional<UserEntity> admin = userRepositoryJPA.findByUsername("admin");
			if(!admin.isPresent()) {
				UserEntity adminUser=new UserEntity().builder()
				.username("admin")
				.password("$2a$10$qQWsDs2i7N1qDx61DK6W1Ou8hEmUcl5QtQ53tE4Hiw2o2lsRwj1Fi") // admin
				.role(roleAdmin.get())
				.isEnabled(true) // cuenta activa
				.accountNoExpired(true) // cuenta no expirada
				.accountNoLocked(true) // cuenta no bloqueada
				.credentialNoExpired(true) // credenciales no expiradas
				.build();
				
				userRepositoryJPA.save(adminUser);
			}
			
			
		};
		
	}

}
