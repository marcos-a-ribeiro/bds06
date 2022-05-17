package com.devsuperior.movieflix.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.GenreDTO;
import com.devsuperior.movieflix.entities.Genre;
import com.devsuperior.movieflix.repositories.GenreRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class GenreService {
	
	@Autowired
	private GenreRepository repository;
	
	@Transactional(readOnly = true)
	public GenreDTO findById(Long id) {
		
		Optional<Genre> obj = repository.findById(id);
		Genre entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new GenreDTO(entity); 
	}	

	@Transactional(readOnly = true)
	public List<GenreDTO> findAll() {
		Optional<List<Genre>> obj = repository.findAllByOrderByIdAsc();
		List<Genre> genres = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return genres.stream().map(Genre -> new GenreDTO(Genre)).collect(Collectors.toList());
	}	
	

}
