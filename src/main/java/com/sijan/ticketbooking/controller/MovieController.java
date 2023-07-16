package com.sijan.ticketbooking.controller;

import com.sijan.ticketbooking.entity.Movie;
import com.sijan.ticketbooking.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
public class MovieController {

    private MovieService movieService;

    @Autowired
    public MovieController(MovieService movieService) {

        this.movieService = movieService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("movies")
    public Movie addMovie(@RequestBody Movie movie) {
        return movieService.addMovie(movie);
    }

    @GetMapping("movies")
    public List<Movie> getAllMovieList(){
        return movieService.getAllMovies();
    }

    @GetMapping("movies/{id}")
    public Movie getMovieWithId(@PathVariable("id") Long id) {
        return movieService.getMovieById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("movies/{id}")
    public Movie updateMovie(@RequestBody Movie movie,@PathVariable("id") Long id){
        return movieService.updateMovie(movie, id);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("movies/{id}")
    public void deleteMovie(@PathVariable("id") Long id){
        movieService.deleteMovie(id);
    }
}
