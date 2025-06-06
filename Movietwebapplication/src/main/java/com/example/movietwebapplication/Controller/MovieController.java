package com.example.movietwebapplication.Controller;

import com.example.movietwebapplication.DTO.FavoriteMovieDTO;
import com.example.movietwebapplication.DTO.MovieDetailsDTO;
import com.example.movietwebapplication.Service.TMDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor
public class MovieController {

    private final TMDBService tmdbService ;


    //getAllMovies
    @GetMapping("/get-all-movies")
    public ResponseEntity<?> getAllMovies() {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.getAllMovies());
    }
    @GetMapping("/get-movie-list")
    public ResponseEntity<?> getMoviesList() {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.getMoviesList());
    }


    //
//getLatestMovieDetails
    @GetMapping("/get-latest-movie-details")
    public ResponseEntity<?> getLatestMovieDetails() {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.getLatestMovieDetails());
    }

    //getSimilarMovies
    @GetMapping("/get-similar-movies/{movieId}")
    public ResponseEntity<?> getSimilarMovies(@PathVariable String movieId) {

        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.getSimilarMovies(movieId));
    }

    //getMovieDetails
    @GetMapping("/get-details-movies/{movieId}")
    public ResponseEntity<?> getMovieDetails(@PathVariable String movieId) {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.getMovieDetails(movieId));
    }
    //getMovieCast
    @GetMapping("/get-movie-cast/{movieId}")
    public ResponseEntity<?> getMovieCast(@PathVariable String movieId) {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.getMovieCast(movieId));
    }

//getCastDetails

    @GetMapping("/get-cast-details/{castId}")
    public ResponseEntity<?> getCastDetails(@PathVariable String castId) {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.getCastDetails(castId));
    }

    //searchMovies
    @GetMapping("/search-movies/{keyword}")
    public ResponseEntity<?> searchMovies(@PathVariable String keyword) {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.searchMovies(keyword));
    }
    //addOrRemoveFavorite
    @PostMapping("/account/{accountId}/favorite")
    public ResponseEntity<?> favoriteMovie(@PathVariable Integer accountId, @RequestBody FavoriteMovieDTO favoriteMovieDTO) {
        return ResponseEntity.status(HttpStatus.OK).body(tmdbService.addOrRemoveFavorite(accountId, favoriteMovieDTO));
    }
    @GetMapping("/get-my-favorites")
    public ResponseEntity<List<MovieDetailsDTO>> getFavorites() {
        String accountId = "22024954";//  myaccount

        return  ResponseEntity.status(HttpStatus.OK).body(tmdbService.getFavoriteMovies(accountId));
    }
}