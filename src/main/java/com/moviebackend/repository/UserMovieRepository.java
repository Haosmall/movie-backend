package com.moviebackend.repository;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebackend.entity.UserMovie;

public interface UserMovieRepository extends JpaRepository<UserMovie, Integer> {

	Page<UserMovie> findAllByUserUsernameContaining(String username, Pageable pageable);
	Page<UserMovie> findAll(Pageable pageable);
}