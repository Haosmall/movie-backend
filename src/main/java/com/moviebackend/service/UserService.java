package com.moviebackend.service;

import java.util.List;
import org.springframework.web.multipart.MultipartFile;

import com.moviebackend.dto.LoginRequestDTO;
import com.moviebackend.dto.LoginResponseDTO;
import com.moviebackend.dto.PaginationWrapper;
import com.moviebackend.dto.UserDTO;
import com.moviebackend.dto.UserRequestDTO;

public interface UserService {
	LoginResponseDTO login(LoginRequestDTO loginRequestDTO);
	
	LoginResponseDTO register(UserDTO userDTO);
	
	UserDTO getUserById(Integer userId);

	UserDTO getUserProfile();
	
	String uploadAvatar(MultipartFile image);
	
	PaginationWrapper<List<UserDTO>> getList(String username, int page, int size);
	
	UserDTO updateUser(UserRequestDTO userRequestDTO);
	
	void delete(int id);

}
