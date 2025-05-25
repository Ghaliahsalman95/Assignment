package com.example.movietwebapplication.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor //this DTO model for json cast
@JsonIgnoreProperties(ignoreUnknown = true)
public class CastDetailsDTO {
    @JsonProperty("id")
    private String id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("biography")//json cast
    private String biography;
    @JsonProperty("birthday")//json cast
    private String birthday;
    @JsonProperty("place_of_birth")
    private String placeOfBirth;
    @JsonProperty("profile_path")
    private String profileImage;   // cast's profile picture
    private List<CastMovieDTO> movies;  // movies this actor has cast in
}
