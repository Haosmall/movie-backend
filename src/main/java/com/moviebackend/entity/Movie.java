package com.moviebackend.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.ForeignKey;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Movie {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	private String name;
	private String description;
	private String year;
	private String image;
	private String duration;
	private String slug;
	private String cast;
	
	@ManyToOne
	@JoinColumn(name = "type_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_movie_type"))
	private Type type;
	
	public Movie(Integer id) {
		this.id = id;
	}
}
