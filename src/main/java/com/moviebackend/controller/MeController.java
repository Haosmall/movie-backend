package com.moviebackend.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moviebackend.dto.UserDTO;
import com.moviebackend.dto.UserRequestDTO;
import com.moviebackend.service.UserService;

@RestController
@RequestMapping("/me")
@CrossOrigin
public class MeController {

	@Autowired
	private UserService userService;
	

	@GetMapping()
	public UserDTO getUserProfile() {
		return userService.getUserProfile();
	}

	@PutMapping()
	public UserDTO updateUser(@Valid @RequestBody UserRequestDTO userRequestDTO) {
		return userService.updateUser(userRequestDTO);
	}

	@PutMapping("/image")
	public String updateImage(@RequestPart("image") MultipartFile image) {

		return userService.uploadAvatar(image);
	}
	
}