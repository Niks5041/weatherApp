package ru.anikson.example.weatherapp.client;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.anikson.example.weatherapp.entity.dto.GeolocationResponse;
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
        GeolocationResponse[] response = restTemplate.getForObject(url, GeolocationResponse[].class);

        if (response != null && response.length > 0) {
            return new double[]{response[0].lat(), response[0].lon()};
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
