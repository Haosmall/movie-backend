package com.moviebackend.service;

import java.util.List;

import com.moviebackend.dto.TypeDTO;

public interface TypeService {
	List<TypeDTO> getAll();

	TypeDTO save(TypeDTO typeDTO);

	void delete(Integer id);
}
