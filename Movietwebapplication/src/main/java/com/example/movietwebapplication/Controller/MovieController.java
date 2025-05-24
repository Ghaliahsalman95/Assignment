package com.example.movietwebapplication.Controller;

import com.example.movietwebapplication.DTO.FavoriteMovieDTO;
import com.example.movietwebapplication.Service.TMDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/movie/")
@RequiredArgsConstructor
public class MovieController {

    private final TMDBService tmdbService ;
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

    @PostMapping("/account/{accountId}/favorite")
    public String favoriteMovie(@PathVariable Integer accountId, @RequestBody FavoriteMovieDTO favoriteMovieDTO) {
        return tmdbService.addOrRemoveFavorite(accountId, favoriteMovieDTO);
    }
}
