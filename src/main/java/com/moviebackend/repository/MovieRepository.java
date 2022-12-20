package com.moviebackend.repository;


import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebackend.entity.Movie;


public interface MovieRepository extends JpaRepository<Movie, Integer> {

	Optional<Movie> findBySlug(String slug);

	Page<Movie> findAllByNameContainingAndTypeSlugContaining(String name, String typeSlug, Pageable pageable);

	boolean existsByIdNotAndName(Integer id, String name);
}