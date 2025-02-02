package ru.anikson.example.weatherapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.anikson.example.weatherapp.entity.Location;
import ru.anikson.example.weatherapp.entity.User;
import ru.anikson.example.weatherapp.service.Location.LocationService;
import ru.anikson.example.weatherapp.service.User.UserService;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/locations")
@RequiredArgsConstructor
public class LocationController {

    private final LocationService locationService;
    private final UserService userService;

    @PostMapping("/add-location")
    @ResponseStatus(HttpStatus.CREATED)
    public String addLocation(@RequestParam String city, HttpServletRequest request) {
        Optional<User> authenticatedUser = userService.isUserAuthenticated(request);
        if (authenticatedUser.isEmpty()) {
            log.warn("Пользователь не авторизован");
            return "redirect:/login";
        }

        Integer userId = authenticatedUser.get().getId();
        locationService.addLocation(city, userId);
        locationService.addLocation(city, userId);

        log.info("Локация добавлена: {}", city);

        // После добавления локации перенаправляем обратно на главную страницу
        return "redirect:/home";
    }

    // Получить все локации для авторизованного пользователя
    @GetMapping("/home")
    public List<Location> home(HttpServletRequest request) {
        // Получаем id пользователя из сессии
        Integer userId = userService.isUserAuthenticated(request).get().getId();

        // Получаем все локации для пользователя
        return locationService.getAllLocations(userId);
    }
}
