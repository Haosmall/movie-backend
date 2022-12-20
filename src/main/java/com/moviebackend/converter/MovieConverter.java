package com.moviebackend.converter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.moviebackend.dto.MovieDTO;
import com.moviebackend.entity.Movie;
import com.moviebackend.entity.Type;
import com.moviebackend.repository.TypeRepository;
import com.moviebackend.utils.CommonFuc;

@Component
public class MovieConverter {
	
	@Autowired
	private TypeRepository typeRepository;
	
	public MovieDTO toMovieDTO(Movie movie) {

		MovieDTO movieDTO = new MovieDTO();

		movieDTO.setId(movie.getId());
		movieDTO.setName(movie.getName());
		movieDTO.setDescription(movie.getDescription());
		movieDTO.setImage(movie.getImage());
		movieDTO.setDuration(movie.getDuration());
		movieDTO.setCast(movie.getCast());
		movieDTO.setSlug(movie.getSlug());
		movieDTO.setTypeId(movie.getType().getId());
		return movieDTO;
	}

	public Movie toMovie(MovieDTO movieDTO) {

		Movie movie = new Movie();
		movie.setId(movieDTO.getId());

		String name = movieDTO.getName();
		movie.setName(movieDTO.getName());
		movie.setDescription(movieDTO.getDescription());
		movie.setYear(movieDTO.getYear());
		movie.setImage(movieDTO.getImage());
		movie.setDuration(movieDTO.getDuration());
		movie.setDuration(movieDTO.getDuration());
		
		Type type = typeRepository.findById(movieDTO.getTypeId()).get();
		movie.setType(type);
		movie.setSlug(CommonFuc.toSlug(name));

		return movie;
	}
}
