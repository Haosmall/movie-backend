package com.moviebackend.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.moviebackend.entity.Type;


public interface TypeRepository extends JpaRepository<Type, Integer> {

	boolean existsByIdNotAndName(Integer id, String name);
}