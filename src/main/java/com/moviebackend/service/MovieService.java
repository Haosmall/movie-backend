package com.moviebackend.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.moviebackend.dto.MovieDTO;
import com.moviebackend.dto.PaginationWrapper;

public interface MovieService {
	PaginationWrapper<List<MovieDTO>> getList(String name, String typeName, int page, int size);

	MovieDTO save(MovieDTO movieDTO);

	void delete(Integer id);

	String uploadImage(Integer movieID, MultipartFile image);
}
