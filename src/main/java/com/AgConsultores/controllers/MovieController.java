package com.AgConsultores.controllers;

import com.AgConsultores.models.Movie;
import com.AgConsultores.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @GetMapping
    public List<Movie> getAllMovies(){
        return  movieRepository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById( @PathVariable long id){
        Optional<Movie> movie = movieRepository.findById(id);
        return  movie.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

}
