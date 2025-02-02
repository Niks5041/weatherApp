package ru.anikson.example.weatherapp.service.Weather;


import ru.anikson.example.weatherapp.entity.dto.WeatherResponse;

public interface WeatherService {

    WeatherResponse getWeatherByCity(String city);

    double[] getCoordinatesByCity(String city);
}
