package ru.anikson.example.weatherapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.anikson.example.weatherapp.entity.User;
import ru.anikson.example.weatherapp.entity.dto.WeatherResponse;
import ru.anikson.example.weatherapp.service.Location.LocationService;
import ru.anikson.example.weatherapp.service.User.UserService;

import java.io.IOException;
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
    public String addLocation(@RequestParam String city,
                              HttpServletRequest request,
                              HttpServletResponse response) {
        Optional<User> authenticatedUser = userService.isUserAuthenticated(request);
        if (authenticatedUser.isEmpty()) {
            log.warn("Пользователь не авторизован");
            return "redirect:/login";
        }

        Integer userId = authenticatedUser.get().getId();
        locationService.addLocation(city, userId);

        log.info("Локация добавлена: {}", city);

        // После добавления локации перенаправляем обратно на главную страницу
        try {
            response.sendRedirect("/home");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    // Получить все локации для авторизованного пользователя
    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        // Получаем id пользователя из сессии
        User user = userService.isUserAuthenticated(request).get();
        // Получаем все локации для пользователя
        List<WeatherResponse> weatherResponses = locationService.getAllLocations(user);
        model.addAttribute("locations", weatherResponses);

        return "home";
    }
}
