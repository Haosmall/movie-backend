package com.moviebackend.entity;

import javax.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class UserMovie_PK {
	
	private static final long serialVersionUID = 1L;

	private Integer user;
	private Integer movie;

}
