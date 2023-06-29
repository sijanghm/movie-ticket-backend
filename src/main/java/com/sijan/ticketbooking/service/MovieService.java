package com.sijan.ticketbooking.service;

import com.sijan.ticketbooking.entity.Movie;
import com.sijan.ticketbooking.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    private MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {

        this.movieRepository = movieRepository;
    }

    public Movie addMovie(Movie movie) {

        return movieRepository.save(movie);
    }

    public List<Movie> getAllMovies() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long id) {
        return movieRepository.findById(id).orElseThrow(()-> new RuntimeException("Movie with id :" +id + " not found"));
    }

    public Movie updateMovie(Movie movie, Long id) {
        Movie exestingMovie = this.getMovieById(id);
        exestingMovie.setMovieName(movie.getMovieName());
        exestingMovie.setDescription(movie.getDescription());
        exestingMovie.setReleaseDate(movie.getReleaseDate());
        exestingMovie.setIsShowing(movie.getIsShowing());
        return movieRepository.save(exestingMovie);
    }

    public void deleteMovie(Long id) {
//        Movie exestingMovie = this.getMovieById(id);
//        movieRepository.delete(exestingMovie);
        movieRepository.deleteById(id);
    }
}
