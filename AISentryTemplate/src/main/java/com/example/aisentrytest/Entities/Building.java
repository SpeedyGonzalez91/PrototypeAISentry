package com.example.aisentrytest.Entities;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Building {
    public String name;
    public String description;
    public List<Floor> floors;

    public Building() {
    }

    @JsonCreator
    public Building(@JsonProperty("name") String name,
                    @JsonProperty("description") String description,
                    @JsonProperty("floors") List<Floor> floors) {
        this.name = name;
        this.description = description;
        this.floors = floors;
    }

    // Getters and Setters

    public String getName() {
        return name;
    }
    public String getDescription() {
        return description;
    }


    public List<Floor> getFloors() {
        return floors;
    }


}
