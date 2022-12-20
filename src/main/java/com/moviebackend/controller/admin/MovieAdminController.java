package com.moviebackend.controller.admin;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.moviebackend.dto.MovieDTO;
import com.moviebackend.exception.MyExceptionHelper;
import com.moviebackend.service.MovieService;

@RestController
@RequestMapping("/admin/movies")
@CrossOrigin
public class MovieAdminController {
	
	@Autowired
	private MovieService movieService;

	@PostMapping(value = "", consumes = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public MovieDTO add(@Valid @RequestBody MovieDTO movieDTO) {

		movieDTO.setId(0);
		return movieService.save(movieDTO);
	}
	
	@PutMapping(value = "/{id}", consumes = "application/json")
	public MovieDTO update(@PathVariable("id") Integer id, @Valid @RequestBody MovieDTO movieDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException("Movie");

		movieDTO.setId(id);
		return movieService.save(movieDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void delete(@PathVariable("id") Integer id) {

		movieService.delete(id);
	}
	
	@PutMapping("/{id}/image")
	public String updateImage(@PathVariable("id") Integer id, @RequestPart("image") MultipartFile image) {
		return movieService.uploadImage(id, image);
	}

}
