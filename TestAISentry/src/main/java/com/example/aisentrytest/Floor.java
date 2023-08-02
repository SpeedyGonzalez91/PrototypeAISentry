package com.example.aisentrytest;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


public class Floor {
    public int floorNumber;
    public List<Place> places;

    @JsonCreator
    public Floor(@JsonProperty("floorNumber") int floorNumber,
                 @JsonProperty("places") List<Place> places) {
        this.floorNumber = floorNumber;
        this.places = places;
    }

    // Getters
    public int getFloorNumber() {
        return floorNumber;
    }

    public List<Place> getPlaces() {
        return places;
    }
}



