package com.example.movietwebapplication.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class MovieDetailsDTO {
    //DTO Out
    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("overview")
    private String description;
    @JsonProperty("poster_path")
    private String poster;
    private List<String> genres;
    @JsonProperty("imdb_id")
    private String imdbLink;
    private List<CastDTO> cast;

}
