package com.faltasproject.security.usuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.faltasproject.security.usuarios.dtos.AuthCreateUser;
import com.faltasproject.security.usuarios.dtos.AuthLoginRequest;
import com.faltasproject.security.usuarios.dtos.AuthReponse;
import com.faltasproject.security.usuarios.dtos.ChangePasswordByUserNameDTO;
import com.faltasproject.security.usuarios.dtos.ChangePasswordProfesorDTO;
import com.faltasproject.security.usuarios.dtos.UserInfo;
import com.faltasproject.security.usuarios.service.UserDetailsServiceImpl;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@PostMapping("/log-in")
	public ResponseEntity<AuthReponse> login(@RequestBody @Valid AuthLoginRequest userRequest){
		return new ResponseEntity<>(this.userDetailsService.loginUser(userRequest), HttpStatus.OK);
	}
	
	@PostMapping("/sign-up")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<AuthReponse> register(@RequestBody @Valid AuthCreateUser authCreateUser) {
		return new ResponseEntity<>(this.userDetailsService.createUser(authCreateUser),HttpStatus.CREATED);
	}
	
	@PostMapping("/change-password-by-ref-prof")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<String> changePasswordByRefProfesor(@RequestBody @Valid ChangePasswordProfesorDTO changePasswordProfesorDTO) {
		this.userDetailsService.changePasswordByreferenciaProfesor(changePasswordProfesorDTO);
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	@PostMapping("/change-password-by-user")
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<String> changePasswordByUser(@RequestBody @Valid ChangePasswordByUserNameDTO changePasswordByUserNameDTO) {
		this.userDetailsService.changePasswordByUsername(changePasswordByUserNameDTO);
		return new ResponseEntity<>("OK",HttpStatus.OK);
	}
	
	@GetMapping("/user-by-ref-profesor")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<String> userByRef(@RequestParam @NotBlank String referenciaProfesor) {
		return new ResponseEntity<>(this.userDetailsService.findUserNameByRefProfesor(referenciaProfesor),HttpStatus.OK);
	}
	
	@GetMapping("/info")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserInfo> info() {
		return new ResponseEntity<>(this.userDetailsService.getuserInfo(),HttpStatus.OK);
	}
	

}
