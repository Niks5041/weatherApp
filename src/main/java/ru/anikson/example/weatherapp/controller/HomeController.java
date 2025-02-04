package ru.anikson.example.weatherapp.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.anikson.example.weatherapp.entity.User;
import ru.anikson.example.weatherapp.service.Location.LocationService;
import ru.anikson.example.weatherapp.service.User.UserService;

import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final LocationService locationService;

    @GetMapping("/home")
    public String home(HttpServletRequest request, Model model) {
        // Проверяем, авторизован ли пользователь
        Optional<User> user = userService.isUserAuthenticated(request);

        // Добавляем атрибут для проверки авторизации
        model.addAttribute("authenticated", user.isPresent());
        model.addAttribute("currentUser", user.orElse(null));

        // Если пользователь не авторизован, перенаправляем на страницу логина
        if (!user.isPresent()) {
            return "home";
        }

        // Получаем локации пользователя
        var locations = locationService.getAllLocations(user.get());
        model.addAttribute("locations", locations);

        // Отображаем домашнюю страницу с локациями
        return "home";
    }
}
