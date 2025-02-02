package ru.anikson.example.weatherapp.service.Location;

import ru.anikson.example.weatherapp.entity.Location;

import java.util.List;

public interface LocationService {

    List<Location> getAllLocations(Integer userId);

    void addLocation(String city, Integer userId);
}
