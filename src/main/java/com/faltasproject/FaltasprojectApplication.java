package com.faltasproject;

import java.util.Optional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.faltasproject.adapters.jpa.usuarios.daos.RoleRepositoryJPA;
import com.faltasproject.adapters.jpa.usuarios.entities.RoleEntity;
import com.faltasproject.adapters.jpa.usuarios.entities.RoleEnum;

@SpringBootApplication
public class FaltasprojectApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaltasprojectApplication.class, args);
	}
	
	@Bean
	CommandLineRunner init(RoleRepositoryJPA roleRepositoryJPA) {
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
			
			
		};
		
	}

}
