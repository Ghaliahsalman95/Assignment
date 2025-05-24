package com.example.movietwebapplication.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FavoriteMovieDTO {

    @Pattern(regexp = "movie|tv", message = "media_type must be either 'movie' or 'tv'")
    @JsonProperty("media_type")
    private String media_type;

    @Positive(message = "media_id must be a positive number")
    @JsonProperty("media_id")
    private Integer media_id;
    private boolean favorite;
}

