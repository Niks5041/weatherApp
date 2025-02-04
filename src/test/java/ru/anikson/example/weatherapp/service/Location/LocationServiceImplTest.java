package ru.anikson.example.weatherapp.service.Location;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anikson.example.weatherapp.dao.LocationRepository;
import ru.anikson.example.weatherapp.dao.UserRepository;
import ru.anikson.example.weatherapp.entity.Location;
import ru.anikson.example.weatherapp.entity.User;
import ru.anikson.example.weatherapp.entity.dto.WeatherResponse;
import ru.anikson.example.weatherapp.service.Weather.WeatherService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LocationServiceImplTest {

    @Mock
    private LocationRepository locationRepository;

    @Mock
    private WeatherService weatherService;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private LocationServiceImpl locationService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1, "testUser", "password", null, null);
    }

    @Test
    void testGetAllLocations() {
        Location location = new Location(1, "Moscow", testUser, 55.7558, 37.6173);
        WeatherResponse weatherResponse = new WeatherResponse();
        weatherResponse.setCity("Moscow");

        when(locationRepository.findAllByUserId(testUser)).thenReturn(List.of(location));
        when(weatherService.getWeatherByCity("Moscow")).thenReturn(weatherResponse);

        List<WeatherResponse> result = locationService.getAllLocations(testUser);
        assertEquals(1, result.size());
        assertEquals("Moscow", result.get(0).getCity());
    }

    @Test
    void testAddLocation() {
        double[] coordinates = {55.7558, 37.6173};
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));
        when(weatherService.getCoordinatesByCity("Moscow")).thenReturn(coordinates);

        locationService.addLocation("Moscow", 1);
        verify(locationRepository, times(1)).save(any(Location.class));
    }
}