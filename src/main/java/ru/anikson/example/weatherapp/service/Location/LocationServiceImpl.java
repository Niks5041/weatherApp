package ru.anikson.example.weatherapp.service.Location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anikson.example.weatherapp.dao.LocationRepository;
import ru.anikson.example.weatherapp.dao.UserRepository;
import ru.anikson.example.weatherapp.entity.Location;
import ru.anikson.example.weatherapp.entity.User;
import ru.anikson.example.weatherapp.entity.dto.WeatherResponse;
import ru.anikson.example.weatherapp.service.Weather.WeatherService;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;
    private final WeatherService weatherService;
    private final UserRepository userRepository;

    @Override
    public List<WeatherResponse> getAllLocations(User user) {
       List<Location> locations = locationRepository.findAllByUserId(user);
       log.info("Пришел запрос на получение списка всех локаций с погодой");

        return locations.stream()
                .map(location ->
                        weatherService.getWeatherByCity(location.getName()))
                .toList();
    }

    @Override
    public void addLocation(String city, Integer userId) {
        User existUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        double[] coordinates = weatherService.getCoordinatesByCity(city);
        Location location = new Location(
                null,
                city,
                existUser,
                coordinates[0],
                coordinates[1]
        );
        locationRepository.save(location);
        log.info("Локация добавлена: {}", location);
    }
}
