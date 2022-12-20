package com.moviebackend.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.moviebackend.converter.TypeConverter;
import com.moviebackend.dto.TypeDTO;
import com.moviebackend.entity.Type;
import com.moviebackend.exception.MyExceptionHelper;
import com.moviebackend.repository.TypeRepository;
import com.moviebackend.service.TypeService;

@Service
@Transactional
public class TypeServiceImpl implements TypeService{
	
	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private TypeConverter typeConverter;

	@Override
	public List<TypeDTO> getAll() {
		return typeRepository.findAll().stream().map(type -> typeConverter.toTypeDTO(type)).collect(Collectors.toList());
	}

	@Override
	public TypeDTO save(TypeDTO typeDTO) {
		validate(typeDTO);

		Type topicWasSave = typeRepository.save(typeConverter.toType(typeDTO));

		return typeConverter.toTypeDTO(topicWasSave);
	}

	@Override
	public void delete(Integer id) {
		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (!typeRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException("Type");

		typeRepository.deleteById(id);
	}
	
	private void validate(TypeDTO typeDTO) {
		if (typeDTO == null || typeDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer id = typeDTO.getId();

		if (id != 0 && !typeRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException("Type");

		if (typeRepository.existsByIdNotAndName(id, typeDTO.getName())) {
			Map<String, String> error = new HashMap<>();
			error.put("name", "name already exist");
			throw MyExceptionHelper.throwRuntimeCustomException(error);
		}
	}

}
