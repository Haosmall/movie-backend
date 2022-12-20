package com.moviebackend.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.moviebackend.utils.MyRegex;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

	@NotNull
	private Integer id;
	
	@NotBlank
	@Size(max = 50)
	@Pattern(regexp = MyRegex.USERNAME_REGEX, message = "Invalid")
	private String name;
}
