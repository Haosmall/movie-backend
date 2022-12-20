package com.moviebackend.converter;

import org.springframework.stereotype.Component;

import com.moviebackend.dto.TypeDTO;
import com.moviebackend.entity.Type;
import com.moviebackend.utils.CommonFuc;

@Component
public class TypeConverter {
	public TypeDTO toTypeDTO(Type type) {

		TypeDTO typeDTO = new TypeDTO();

		typeDTO.setId(type.getId());
		typeDTO.setName(type.getName());
		typeDTO.setSlug(type.getSlug());

		return typeDTO;
	}

	public Type toType(TypeDTO typeDTO) {

		Type type = new Type();
		type.setId(typeDTO.getId());

		String name = typeDTO.getName();
		type.setName(name);
		type.setSlug(CommonFuc.toSlug(name));

		return type;
	}
}
