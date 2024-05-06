package com.faltasproject.security.usuarios.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.faltasproject.security.usuarios.dtos.AuthCreateUser;
import com.faltasproject.security.usuarios.dtos.AuthLoginRequest;
import com.faltasproject.security.usuarios.dtos.AuthReponse;
import com.faltasproject.security.usuarios.dtos.UserInfo;
import com.faltasproject.security.usuarios.service.UserDetailsServiceImpl;

import jakarta.validation.Valid;

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
	public ResponseEntity<AuthReponse> register(@RequestBody @Valid AuthCreateUser authCreateUser) {
		return new ResponseEntity<AuthReponse>(this.userDetailsService.createUser(authCreateUser),HttpStatus.CREATED);
	}
	
	@GetMapping("/info")
	@PreAuthorize("isAuthenticated()")
	public ResponseEntity<UserInfo> info() {
		return new ResponseEntity<UserInfo>(this.userDetailsService.getuserInfo(),HttpStatus.OK);
	}
	

}
