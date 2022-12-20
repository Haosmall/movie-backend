package com.moviebackend.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.moviebackend.converter.MovieConverter;
import com.moviebackend.converter.TypeConverter;
import com.moviebackend.dto.MovieDTO;
import com.moviebackend.dto.PaginationWrapper;
import com.moviebackend.entity.Movie;
import com.moviebackend.exception.MyExceptionHelper;
import com.moviebackend.repository.MovieRepository;
import com.moviebackend.repository.TypeRepository;
import com.moviebackend.service.AwsS3Service;
import com.moviebackend.service.MovieService;
import com.moviebackend.utils.AuthenInfo;
import com.moviebackend.utils.EntityValidator;
import com.moviebackend.utils.FileHelpers;

@Service
@Transactional
public class MovieServiceImpl implements MovieService{
	
	@Autowired
	private MovieRepository movieRepository;
	
	@Autowired
	private TypeRepository typeRepository;

	@Autowired
	private MovieConverter movieConverter;
	
	@Autowired
	private TypeConverter typeConverter;
	
	@Autowired
	private AwsS3Service awsS3Service;

	@Override
	public PaginationWrapper<List<MovieDTO>> getList(String name, String typeSlug, int page, int size) {
		if (name == null || typeSlug == null || page < 0 || size <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Page<Movie> moviesPage = movieRepository.findAllByNameContainingAndTypeSlugContaining(name, typeSlug,
				PageRequest.of(page, size));

		var movieDTOsPageResult = new PaginationWrapper<List<MovieDTO>>();
		movieDTOsPageResult.setPage(page);
		movieDTOsPageResult.setSize(page);
		movieDTOsPageResult.setTotalPages(moviesPage.getTotalPages());

		List<MovieDTO> movieDTOs = moviesPage.toList().stream().map(movieEle -> movieConverter.toMovieDTO(movieEle))
				.collect(Collectors.toList());
		movieDTOsPageResult.setData(movieDTOs);

		return movieDTOsPageResult;
	}

	@Override
	public MovieDTO save(MovieDTO movieDTO) {
		validate(movieDTO);

		Movie movie = movieRepository.save(movieConverter.toMovie(movieDTO));

		return movieConverter.toMovieDTO(movie);
	}

	@Override
	public void delete(Integer id) {
		if (id == null || id <= 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (!movieRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException("Movie");

		movieRepository.deleteById(id);
		
	}

	@Override
	public String uploadImage(Integer movieID,MultipartFile image) {
		AuthenInfo.checkLogin();
		Movie movie = movieRepository.findById(movieID).get();

		if (!FileHelpers.checkImageExtension(image))
			throw MyExceptionHelper.throwIllegalArgumentException();

		if (movie.getImage() != null) {
			awsS3Service.deleteObjectFromUrl(movie.getImage());
		}

		String imageUrl = awsS3Service.uploadObject(image);
		movie.setImage(imageUrl);

		movieRepository.save(movie);

		return imageUrl;
	}

	private void validate(MovieDTO movieDTO) {

		if (movieDTO == null || movieDTO.getId() == null || movieDTO.getId() < 0)
			throw MyExceptionHelper.throwIllegalArgumentException();

		Integer id = movieDTO.getId();
		if (id != 0 && !movieRepository.existsById(id))
			throw MyExceptionHelper.throwResourceNotFoundException("Movie");

		EntityValidator.checkValidate(errors -> {

			if (movieRepository.existsByIdNotAndName(id, movieDTO.getName()))
				errors.put("name", "Movie already exist");

			if (!typeRepository.existsById(movieDTO.getTypeId()))
				errors.put("bookId", "Type not found");

		});

	}

}
