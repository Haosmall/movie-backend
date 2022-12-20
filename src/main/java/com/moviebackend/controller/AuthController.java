package com.moviebackend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.moviebackend.dto.LoginRequestDTO;
import com.moviebackend.dto.LoginResponseDTO;
import com.moviebackend.dto.UserDTO;
import com.moviebackend.service.UserService;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private UserService userService;

	@PostMapping("/registry")
	public LoginResponseDTO register(@Valid @RequestBody UserDTO userDTO) {

		return userService.register(userDTO);
	}

	@PostMapping("/login")
	public LoginResponseDTO login(@RequestBody LoginRequestDTO loginRequestDTO) {

		return userService.login(loginRequestDTO);
	}

}