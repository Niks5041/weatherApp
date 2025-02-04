package ru.anikson.example.weatherapp.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.anikson.example.weatherapp.service.User.UserService;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    // Страница авторизации
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        log.info("Пришел запрос на страницу логина");
        if (userService.isUserAuthenticated(request).isPresent()) {
            return "redirect:/home";
        }
        model.addAttribute("error", "Неверный логин или пароль");
        return "login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpServletResponse response,
                        Model model) {
        log.info("Попытка авторизации пользователя: {}", username);
        boolean isAuthenticated = userService.authenticateUser(username, password, response);

        if (isAuthenticated) {
            log.info("Пользователь {} успешно авторизован", username);
            return "redirect:/home"; // Переход на главную страницу после успешной авторизации
        } else {
            log.error("Неверный логин или пароль");
            model.addAttribute("error", "Неверный логин или пароль");
            return "login"; // В случае ошибки возвращаем на страницу логина
        }
    }


    // Страница регистрации
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    public String register(@RequestParam String login,
                           @RequestParam String password,
                           HttpServletResponse response) {
        log.info("Попытка регистрации пользователя: {}", login);

        boolean isRegistered = userService.registerUser(login, password);

        if (isRegistered) {
            log.info("Регистрация прошла успешно: {}", isRegistered);
            try {
                response.sendRedirect("/login");  // Явный редирект на страницу логина
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return null; // Возвращаем null, потому что редирект уже выполнен
        } else {
            log.error("Ошибка регистрации");
            return "register"; // В случае ошибки возвращаем на страницу регистрации
        }
    }
}
