package com.example.movietwebapplication.Service;

import com.example.movietwebapplication.DTO.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class TMDBService {

    private final RestTemplate restTemplate; // HTTP request handling and response
    private final ObjectMapper objectMapper; // to convert between java objec and JSON


    @Value("${tmdb.api.key}")//get value from properties
    private String apiKey;

    @Value("${tmdb.api.url}")
    private String url;

    @Value("${tmdb.session.id}")
    private final String sessionId="67ac9524cd17a45e65f8c2b6a18cc633a30eb052";

    //• As a user, I can see the latest movies.
    //• As a user, I can search movies.
    //Bonus point to implementing the following user stories:
    //• As a user, I can share movie link to IMDB site.
    //• As a user, I can see similar movies for each movie.
    //• As a user, I can see as more movie details (Cast names and posters).
    //• As a user, I can see star details with the list of movies casted in.

    //helper method

    private String buildUrl(String endpoint, String queryParams) { //helper method for almost methods
        return url + endpoint + "?api_key=" + apiKey + (queryParams != null ? "&" + queryParams : "");
    }

    //helper method
    public JsonNode fetchJson(String endpoint, String keyword) { //retriev data from http request
        String url = buildUrl(endpoint, keyword);
        String response = restTemplate.getForObject(url, String.class);
        try {
            return objectMapper.readTree(response); // convert data from restTemplate to java object ,,
            // i create DTOs likly fields in tmdb
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }


    }
    //• As a user, I can search movies. maybe by keywords
    //https://api.themoviedb.org/3/search/movie?api_key=12530d7ccb5546f2a831d28f1b913ddc&query=inception

    public List<MovieDetailsDTO> searchMovies(String keywords) { // serach movie by keyword
        String queryParams = "query=" + keywords.replace(" ", "%20");
        JsonNode root = fetchJson("/search/movie", queryParams);
        JsonNode  resultsNode = root.get("results");
        if (resultsNode == null || !resultsNode.isArray()) {
            throw new RuntimeException("'results' field missing or not array");
        }
        List<MovieDetailsDTO> movieDetailsDTOList=new ArrayList<>();
        for (JsonNode node : resultsNode) {

            movieDetailsDTOList.add(getMovieDetails(node.path("id").asText()));
        }


        return movieDetailsDTOList;
    }

    //• As a user, I can see the latest movies.
    //https://api.themoviedb.org/3/movie/latest

    public MovieDetailsDTO getLatestMovieDetails() {       //Done
        JsonNode root = fetchJson("/movie/latest", null);
        if (root == null || !root.has("id")) {
            return null; // not found
        }
        return getMovieDetails(root.path("id").asText()); //reused the getMovieDetails by extract movie id


    }

    //• As a user, I can see similar movies for each movie.
    //https://api.themoviedb.org/3/movie/{movie_id}/similar

    public List<MovieDetailsDTO> getSimilarMovies(String movieId) {     //Done
        long start = System.currentTimeMillis();

        JsonNode root = fetchJson("/movie/" + movieId + "/similar", null);
        List<MovieDetailsDTO> movieDetailsDTOS=new ArrayList<>();

        if (root != null && root.has("results")) {
            for (JsonNode movieNode : root.get("results")) {
                String similarMovieId = movieNode.path("id").asText(); // get every similar movie id

                // call MovieDetailsDTO for each similar movie
                MovieDetailsDTO details = getMovieDetails(similarMovieId);
                if (details != null) {
                    movieDetailsDTOS.add(details);
                }
            }
        }
        System.out.println("TMDB API call duration: " + (System.currentTimeMillis() - start) + "ms");
        return movieDetailsDTOS; //this test for how time request take fetch to response
    }

    //• As a user, I can see movie details (Movie Name, Poster, Movie Description, Genres)
    //• As a user, I can share movie link to IMDB site.
    //• As a user, I can see movie details (Movie Name, Poster, Movie Description, Genres)

    //https://api.themoviedb.org/3/movie/{movie_id}
    //• As a user, I can search movies.


    //• As a user, I can see movie details (Movie Name, Poster, Movie Description, Genres)
    //https://api.themoviedb.org/3/movie/{movie_id}
    public MovieDetailsDTO getMovieDetails(String movieId) {  //Done
//
        JsonNode root = fetchJson("/movie/" + movieId, null);
        MovieDetailsDTO movie = new MovieDetailsDTO();
        movie.setId(root.path("id").asText());
        movie.setTitle(root.path("title").asText());
        movie.setDescription(root.path("overview").asText());
        movie.setPoster("https://image.tmdb.org/t/p/w500" + root.path("poster_path").asText());

        List<String> genres = new ArrayList<>();
        for (JsonNode genreNode : root.path("genres")) {
            genres.add(genreNode.path("name").asText());
        }

        movie.setId(movieId);
        movie.setGenres(genres);
        movie.setImdbLink("https://www.imdb.com/title/" + root.path("imdb_id").asText()); //share ImdbLink

        return movie;
    }

    //• As a user, I can see as more movie details (Cast names and posters).
    // https://api.themoviedb.org/3/movie/{movie_id}/credits

    public MovieDetailsDTO getMovieCast(String movieId) {
        List<CastDTO> castList = new ArrayList<>();
        JsonNode root = fetchJson("/movie/" + movieId + "/credits", null);

        JsonNode castArray = root.path("cast");
        MovieDetailsDTO movieDetailsDTO = getMovieDetails(movieId);

        int limit = Math.min(castArray.size(), 10); //this if array less than 10 put it in loop otherwise 10
        for (int i = 0; i < limit; i++) {
            JsonNode castNode = castArray.get(i);

            CastDTO member = new CastDTO();
            member.setName(castNode.path("name").asText());
            member.setCharacter(castNode.path("character").asText());
            member.setId(castNode.path("id").asText());

            String profilePath = castNode.path("profile_path").asText(null);
            if (profilePath != null && !profilePath.equals("null")) {
                member.setProfileImage("https://image.tmdb.org/t/p/w200" + profilePath);
            } else {
                member.setProfileImage(null);
            }

            castList.add(member);
        }

        movieDetailsDTO.setCast(castList);
        return movieDetailsDTO;
    }


    //• As a user, I can see star details with the list of movies casted in.
    //https://api.themoviedb.org/3/person/{person_id}
    public CastDetailsDTO getCastDetails(String castId) {      //Done
        JsonNode castJson = fetchJson("/person/" + castId, null);
        CastDetailsDTO castDetailsDTO = new CastDetailsDTO();
        castDetailsDTO.setId(castJson.path("id").asText("id"));
        castDetailsDTO.setName(castJson.path("name").asText());
        castDetailsDTO.setBiography(castJson.path("biography").asText());
        castDetailsDTO.setBirthday(castJson.path("birthday").asText());
        castDetailsDTO.setPlaceOfBirth(castJson.path("place_of_birth").asText());
        String profilePath = castJson.path("profile_path").asText();
        castDetailsDTO.setProfileImage(
                (profilePath != null && !profilePath.equals("null"))
                        ? "https://image.tmdb.org/t/p/w300" + profilePath
                        : null
        );

        //  movie credits
        String creditsUrl = buildUrl("/person/" + castId + "/movie_credits", null);
        JsonNode creditsJson;
        try {
            creditsJson = objectMapper.readTree(restTemplate.getForObject(creditsUrl, String.class));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        JsonNode castList = creditsJson.path("cast");

        List<CastMovieDTO> movies = new ArrayList<>(); //to show character actor in each movie
        int count =0;
        int limit = Math.min(castList.size(), 10); //this if array less than 10 put it in loop otherwise 10
        for (int i = 0; i < limit; i++) {
            count++;
            CastMovieDTO castDetail = new CastMovieDTO();
            castDetail.setId(castList.get(i).path("id").asText());
            castDetail.setCharacter(castList.get(i).path("character").asText());
            castDetail.setTitle(castList.get(i).path("title").asText());
            castDetail.setPoster("https://image.tmdb.org/t/p/w500" + castList.get(i).path("poster_path").asText());
            movies.add(castDetail);
        }
        castDetailsDTO.setMovies(movies);
        return castDetailsDTO;
    }

    //this endpoint i wrote full api because i have one api post method
    //https://api.themoviedb.org/3/account/22024954/favorite?session_id=67ac9524cd17a45e65f8c2b6a18cc633a30eb052&api_key=12530d7ccb5546f2a831d28f1b913ddc
    public String addOrRemoveFavorite(Integer accountId, FavoriteMovieDTO favoriteRequest) {
        String url = "https://api.themoviedb.org/3/account/{accountId}/favorite"
                + "?api_key={apiKey}&session_id={sessionId}";

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<FavoriteMovieDTO> entity = new HttpEntity<>(favoriteRequest, headers);

        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class, accountId, apiKey, sessionId
        );

        return response.getBody();
    }

    public JsonNode getMoviesList() {
        return fetchJson("/movie",null);
    }

    public List<MovieDetailsDTO> getFavoriteMovies(String accountId) {
        long start = System.currentTimeMillis();

        String queryParams = "session_id=" + sessionId;

        // Fetch favorite movie IDs
        JsonNode root = fetchJson("/account/" + accountId + "/favorite/movies", queryParams);
        List<MovieDetailsDTO> favoriteMovies = new ArrayList<>();

        if (root != null && root.has("results")) {
            for (JsonNode movieNode : root.get("results")) {
                String movieId = movieNode.path("id").asText(); // movieId as String

                // Get full movie details
                MovieDetailsDTO details = getMovieDetails(movieId);
                if (details != null) {
                    favoriteMovies.add(details);
                }
            }
        }

        long duration = System.currentTimeMillis() - start;
        System.out.println("Fetched favorite movies in " + duration + "ms");

        return favoriteMovies;
    }

    public List<MovieDetailsDTO> getAllMovies() {
        String queryParams = "page=" + 1 + "&sort_by=popularity.desc";
        JsonNode root = fetchJson("/discover/movie", queryParams);

        List<MovieDetailsDTO> movies = new ArrayList<>();
        JsonNode results = root.path("results");

        for (JsonNode movie : results) {
            MovieDetailsDTO dto = new MovieDetailsDTO();
            dto.setId(movie.path("id").asText());
            dto.setTitle(movie.path("title").asText());
            dto.setDescription(movie.path("overview").asText());
            dto.setPoster("https://image.tmdb.org/t/p/w500" + movie.path("poster_path").asText());
            dto.setImdbLink("https://www.imdb.com/title/" + root.path("imdb_id").asText()); //share ImdbLink
            movies.add(dto);
        }

        return movies;
    }

}
