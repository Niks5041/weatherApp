package ru.anikson.example.weatherapp.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.anikson.example.weatherapp.entity.dto.WeatherResponse;


@Component
@RequiredArgsConstructor
public class WeatherClient {

    private final RestTemplate restTemplate;

    @Value("${openweathermap.api.key}")
    private String apiKey;

    @Value("${openweathermap.api.url}")
    private String apiUrl;

    @Value("${openweathermap.geocoding.url}")
    private String geocodingUrl;

    /**
     * Получает координаты города
     */
    public double[] getCoordinatesByCity(String city) {
        String url = String.format("%s/weather?q=%s&appid=%s", geocodingUrl, city, apiKey);
        WeatherResponse response = restTemplate.getForObject(url, WeatherResponse.class);

        if (response != null) {
            return new double[]{response.getCoord().getLat(), response.getCoord().getLon()};
        }
        throw new RuntimeException("Не удалось получить координаты для города: " + city);
    }

    /**
     * Получает погоду по координатам
     */
    public WeatherResponse getWeatherByCoordinates(double lat, double lon) {
        String url = String.format("%s/weather?lat=%f&lon=%f&units=metric&appid=%s", apiUrl, lat, lon, apiKey);
        return restTemplate.getForObject(url, WeatherResponse.class);
    }
}
