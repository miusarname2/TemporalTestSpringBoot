package com.AgConsultores.controllers;

import com.AgConsultores.models.Movie;
import com.AgConsultores.repositories.MovieRepository;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/movies")
public class MovieController {

    @Autowired
    private MovieRepository movieRepository;

    @CrossOrigin
    @GetMapping
    public List<Movie> getAllMovies(){
        return  movieRepository.findAll();
    }

    @CrossOrigin
    @GetMapping("/{id}")
    public ResponseEntity<Movie> getMovieById( @PathVariable long id){
        Optional<Movie> movie = movieRepository.findById(id);
        return  movie.map(ResponseEntity::ok).orElseGet(()-> ResponseEntity.notFound().build());
    }

    @CrossOrigin
    @PostMapping
    public ResponseEntity<Movie> createMovie(@RequestBody Movie movie){
        Movie saveMovie = movieRepository.save(movie);

        return ResponseEntity.status(HttpStatus.CREATED).body(saveMovie);
    }

    @CrossOrigin
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMovie(@PathVariable long id){
        if (!movieRepository.existsById(id)) {
            return  ResponseEntity.notFound().build();
        }
        movieRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @CrossOrigin
    @PutMapping("/{id}")
    public ResponseEntity<Movie> updateMovie(@PathVariable long id, @RequestBody Movie movieNewData){
        if (!movieRepository.existsById(id)) {
            return  ResponseEntity.notFound().build();
        }
        movieNewData.setId(id);
        Movie changedMovie = movieRepository.save(movieNewData);
        return ResponseEntity.ok(changedMovie);
    }
}
