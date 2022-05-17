package com.devsuperior.movieflix.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devsuperior.movieflix.entities.Genre;

@Repository
public interface GenreRepository extends JpaRepository<Genre, Long> {
	
	public Optional<List<Genre>> findAllByOrderByIdAsc();
	
}
