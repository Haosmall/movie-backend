package com.moviebackend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserMovieDTO {

	private Integer id;
	
	@NotBlank
	@Size(max = 50)
	private String name;
	
	private String description;
	
	@NotBlank
	private String year;
	
	private String image;
	
	private String duration;
	
	private String cast;
	
	@JsonProperty(access = Access.READ_ONLY)
	private String slug;
	
	@NotBlank
	private String typeId;
	
//	@JsonProperty(access = Access.READ_ONLY)
//	private List<E> slug;
	
}
