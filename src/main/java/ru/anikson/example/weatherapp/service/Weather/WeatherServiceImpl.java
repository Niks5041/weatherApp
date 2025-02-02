package ru.anikson.example.weatherapp.service.Weather;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.anikson.example.weatherapp.client.WeatherClient;
import ru.anikson.example.weatherapp.entity.dto.WeatherResponse;


@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

    private final WeatherClient weatherClient;

    @Override
    public WeatherResponse getWeatherByCity(String city) {
        double[] coordinates = getCoordinatesByCity(city);
        return weatherClient.getWeatherByCoordinates(coordinates[0], coordinates[1]);
    }

    @Override
    public double[] getCoordinatesByCity(String city) {
        return weatherClient.getCoordinatesByCity(city);
    }
}
