package com.moviebackend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.moviebackend.dto.UserDTO;
import com.moviebackend.dto.UserRequestDTO;
import com.moviebackend.entity.User;
import com.moviebackend.entity.UserRole;
import com.moviebackend.repository.UserRepository;

@Component
public class UserConverter {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserRepository userRepository;
	
	public UserDTO toUserDTO(User user) {

		var userDTO = new UserDTO();
		userDTO.setId(user.getId());
		userDTO.setName(user.getName());
		userDTO.setUsername(user.getUsername());
		userDTO.setAvatar(user.getAvatar());
		userDTO.setRole(user.getRole().toString());

		return userDTO;
	}
	
	public User toRegisterUser(UserDTO userDTO) {

		User user = new User();
		user.setName(userDTO.getName());
		user.setUsername(userDTO.getUsername());

		String password = passwordEncoder.encode(userDTO.getPassword());
		user.setPassword(password);
		
		user.setRole(UserRole.participate);

		return user;
	}
	
	public User toUser(UserRequestDTO userRequestDTO) {

		User user = userRepository.findById(userRequestDTO.getId()).orElse(new User(userRequestDTO.getId()));
		user.setName(userRequestDTO.getName());

		return user;
	}
}
