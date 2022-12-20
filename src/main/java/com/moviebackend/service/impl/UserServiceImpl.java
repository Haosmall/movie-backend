package com.moviebackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moviebackend.config.jwt.JwtTokenProvider;
import com.moviebackend.converter.UserConverter;
import com.moviebackend.dto.LoginRequestDTO;
import com.moviebackend.dto.LoginResponseDTO;
import com.moviebackend.dto.PaginationWrapper;
import com.moviebackend.dto.UserDTO;
import com.moviebackend.dto.UserRequestDTO;
import com.moviebackend.entity.User;
import com.moviebackend.exception.MyExceptionHelper;
import com.moviebackend.repository.UserRepository;
import com.moviebackend.service.AwsS3Service;
import com.moviebackend.service.UserService;
import com.moviebackend.utils.AuthenInfo;
import com.moviebackend.utils.FileHelpers;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private JwtTokenProvider jwtTokenProvider;
	
	@Autowired
	private UserConverter userConverter;
	
	@Autowired
	private AwsS3Service awsS3Service;
	
	private String LOGIN_FAILED_MESSAGE = "Incorrect username or password";

	@Override
	public LoginResponseDTO login(LoginRequestDTO loginRequestDTO) {
		User user = userRepository.findByUsername(loginRequestDTO.getUsername())
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException(LOGIN_FAILED_MESSAGE));

		if (!passwordEncoder.matches(loginRequestDTO.getPassword(), user.getPassword()))
			throw MyExceptionHelper.throwResourceNotFoundException(LOGIN_FAILED_MESSAGE);

		String accessToken = jwtTokenProvider.generateToken(user.getId());
		
		UserDTO userDTO = userConverter.toUserDTO(user);
		return new LoginResponseDTO(userDTO, accessToken);
	}

	@Override
	public LoginResponseDTO register(UserDTO userDTO) {
		String username = userDTO.getUsername();

		if (userRepository.findByUsername(username).isPresent())
			throw MyExceptionHelper.throwRuntimeCustomException("User is already exist");
		
		User user = userRepository.save(userConverter.toRegisterUser(userDTO));
		
		String accessToken = jwtTokenProvider.generateToken(user.getId());
		
		return new LoginResponseDTO( userConverter.toUserDTO(user), accessToken);
	}

	@Override
	public UserDTO getUserById(Integer userId) {
		if (userId == null || userId <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();
		
		User user = userRepository.findById(userId).get();
		return userConverter.toUserDTO(user);
	}

	@Override
	public UserDTO getUserProfile() {
		AuthenInfo.checkLogin();
		String username = AuthenInfo.getUsername();
		User user = userRepository.findByUsername(username).get();
		return userConverter.toUserDTO(user);
	}

	@Override
	public String uploadAvatar(MultipartFile image) {
		AuthenInfo.checkLogin();
		String username = AuthenInfo.getUsername();
		User user = userRepository.findByUsername(username).get();

		if (!FileHelpers.checkImageExtension(image))
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (user.getAvatar() != null) {
			awsS3Service.deleteObjectFromUrl(user.getAvatar());
		}

		String avatarUrl = awsS3Service.uploadObject(image);
		user.setAvatar(avatarUrl);

		userRepository.save(user);

		return avatarUrl;
	}

	@Override
	public PaginationWrapper<List<UserDTO>> getList(String username, int page, int size) {
		if (username == null || page < 0 || size <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Page<User> usersPage = userRepository.findAllByUsernameContaining(username, PageRequest.of(page, size));

		var usersPageResult = new PaginationWrapper<List<UserDTO>>();
		usersPageResult.setPage(page);
		usersPageResult.setSize(size);
		usersPageResult.setTotalPages(usersPage.getTotalPages());

		List<UserDTO> userDTOs = usersPage.toList().stream().map(user -> userConverter.toUserDTO(user))
				.collect(Collectors.toList());
		usersPageResult.setData(userDTOs);

		return usersPageResult;
	}

	@Override
	public UserDTO updateUser(UserRequestDTO userRequestDTO) {
		User user = userConverter.toUser(userRequestDTO);
		userRepository.save(user);
		
		return userConverter.toUserDTO(user);
	}

	@Override
	public void delete(int id) {
		getUser(id);
		userRepository.deleteById(id);
	}
	
	private User getUser(int id) {

		if (id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		return userRepository.findById(id)
				.orElseThrow(() -> MyExceptionHelper.throwResourceNotFoundException("User"));
	}

}
