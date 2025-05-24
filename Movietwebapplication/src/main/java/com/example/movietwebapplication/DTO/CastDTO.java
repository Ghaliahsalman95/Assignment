package com.example.movietwebapplication.DTO;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)

public class CastDTO {
    @JsonProperty("name")
    private String name;
    @JsonProperty("character")
    private String character;
    @JsonProperty("profile_path")
    private String profileImage;

}
