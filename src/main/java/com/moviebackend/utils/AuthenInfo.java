package com.moviebackend.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import com.moviebackend.entity.User;
import com.moviebackend.exception.MyExceptionHelper;
import com.moviebackend.repository.UserRepository;


@Component
public class AuthenInfo {

	@Autowired
	private UserRepository userRepository;

	public static String getUsername() {
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}

	public static boolean isLogin() {

		return !getUsername().equals("anonymousUser");
	}

	public User getUser() {

		return userRepository.findByUsername(getUsername()).orElse(null);
	}

	public static void checkLogin() {

		if (!isLogin())
			throw MyExceptionHelper.throwAuthenticationException();
	}
}