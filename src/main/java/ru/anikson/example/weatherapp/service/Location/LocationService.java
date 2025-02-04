package ru.anikson.example.weatherapp.service.Location;

import ru.anikson.example.weatherapp.entity.User;
import ru.anikson.example.weatherapp.entity.dto.WeatherResponse;

import java.util.List;

public interface LocationService {

    List<WeatherResponse> getAllLocations(User user);

    void addLocation(String city, Integer userId);

}
