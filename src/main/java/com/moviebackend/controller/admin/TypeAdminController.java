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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.moviebackend.dto.TypeDTO;
import com.moviebackend.exception.MyExceptionHelper;
import com.moviebackend.service.TypeService;

@RestController
@RequestMapping("/admin/types")
@CrossOrigin
public class TypeAdminController {
	
	@Autowired
	private TypeService typeService;

	@PostMapping(value = "", consumes = "application/json")
	@ResponseStatus(code = HttpStatus.CREATED)
	public TypeDTO addNewType(@Valid @RequestBody TypeDTO typeDTO) {

		typeDTO.setId(0);
		return typeService.save(typeDTO);
	}

	@PutMapping(value = "/{id}", consumes = "application/json")
	public TypeDTO updateType(@PathVariable("id") Integer id, @Valid @RequestBody TypeDTO typeDTO) {

		if (id <= 0)
			throw MyExceptionHelper.throwResourceNotFoundException("Type");

		typeDTO.setId(id);
		return typeService.save(typeDTO);
	}

	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void deleteType(@PathVariable("id") Integer id) {

		typeService.delete(id);
	}

}
