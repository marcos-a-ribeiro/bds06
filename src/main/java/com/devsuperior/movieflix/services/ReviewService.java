package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.client.HttpClientErrorException.UnprocessableEntity;

import com.devsuperior.movieflix.dto.ReviewDTO;
import com.devsuperior.movieflix.entities.Movie;
import com.devsuperior.movieflix.entities.Review;
import com.devsuperior.movieflix.repositories.ReviewRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class ReviewService {
	
	@Autowired
	private ReviewRepository repository;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private MovieService movieService;
	
	@Transactional(readOnly = true)
	public ReviewDTO findById(Long id) {
		Optional<Review> obj = repository.findById(id);
		Review entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new ReviewDTO(entity); 
	}
	
	@Transactional(readOnly = true)
	public List<ReviewDTO> findAllByMovieId(Long id) {
		Optional<List<Review>> obj = repository.findAllByMovieId(id);
		
		List<Review> listReview = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		List<ReviewDTO> list = listReview.stream().map(review -> new ReviewDTO(review)).collect(Collectors.toList());
		return list; 
	}
	
	@Transactional
	public ReviewDTO insert(ReviewDTO dto) {
			Movie movie = new Movie( movieService.findById(dto.getMovieId()));
			Review entity = new Review();
			entity.setText(dto.getText());
			entity.setMovie(movie);
			entity.setUser(authService.authenticated());
			entity = repository.save(entity);
			return new ReviewDTO(entity);
	}
	
}
