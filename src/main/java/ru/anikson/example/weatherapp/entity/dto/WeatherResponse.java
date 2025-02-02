package ru.anikson.example.weatherapp.entity.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherResponse {

    @JsonProperty("name")
    private String city;

    @JsonProperty("coord")
    private Coordinates coord;

    @JsonProperty("weather")
    private WeatherInfo[] weather;

    @JsonProperty("main")
    private MainInfo main;

    @JsonProperty("visibility")
    private int visibility;

    @JsonProperty("wind")
    private WindInfo wind;

    @JsonProperty("rain")
    private RainInfo rain;

    @JsonProperty("clouds")
    private CloudsInfo clouds;

    @JsonProperty("sys")
    private SysInfo sys;

    @JsonProperty("timezone")
    private int timezone;

    @Data
    public static class Coordinates {
        private double lon;
        private double lat;
    }

    @Data
    public static class WeatherInfo {
        private int id;
        private String main;
        private String description;
        private String icon;
    }

    @Data
    public static class MainInfo {
        private double temp;
        private double feelsLike;
        private double tempMin;
        private double tempMax;
        private int pressure;
        private int humidity;
        private int seaLevel;
        private int grndLevel;
    }

    @Data
    public static class WindInfo {
        private double speed;
        private int deg;
        private double gust;
    }

    @Data
    public static class RainInfo {
        @JsonProperty("1h")
        private double rain1h;
    }

    @Data
    public static class CloudsInfo {
        private int all;
    }

    @Data
    public static class SysInfo {
        private int type;
        private int id;
        private String country;
        private int sunrise;
        private int sunset;
    }
}
