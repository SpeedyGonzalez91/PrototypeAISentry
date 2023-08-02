package com.example.aisentrytest.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Place {
    public String name;
    public String description;
    public String directions;
    public String category;


    @JsonCreator
    public Place(@JsonProperty("name") String name,
                 @JsonProperty("description") String description,
                 @JsonProperty("directions") String directions,
                 @JsonProperty("category") String category) {
        this.name = name;
        this.description = description;
        this.directions = directions;
        this.category = category;
    }
    // Getters and Setters

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getDirections() {
        return directions;
    }

    public String getCategory() {
        return category;
    }
}
