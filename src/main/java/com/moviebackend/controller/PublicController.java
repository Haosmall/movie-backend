package com.moviebackend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moviebackend.dto.MovieDTO;
import com.moviebackend.dto.PaginationWrapper;
import com.moviebackend.dto.TypeDTO;
import com.moviebackend.service.MovieService;
import com.moviebackend.service.TypeService;

@RestController
@RequestMapping()
@CrossOrigin
public class PublicController {
	
	@Autowired
	private TypeService typeService;
	
	@Autowired
	private MovieService movieService;

	@GetMapping("/types")
	public List<TypeDTO> getListMovieType() {
		return typeService.getAll();
	}
	
	@GetMapping("/movies")
	public PaginationWrapper<List<MovieDTO>> getListMovie(@RequestParam(name = "name", required = false, defaultValue = "") String name,
			@RequestParam(name = "typeSlug", required = false, defaultValue = "") String typeSlug,
			@RequestParam(name = "page", required = false, defaultValue = "0") int page,
			@RequestParam(name = "size", required = false, defaultValue = "12") int size) {

		return movieService.getList(name, typeSlug, page, size);
	}
}