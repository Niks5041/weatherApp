package ru.anikson.example.weatherapp.controller;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.anikson.example.weatherapp.service.User.UserService;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UserService userService;

    // Страница авторизации
    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model) {
        if (userService.isUserAuthenticated(request).isPresent()) {
            return "redirect:/home";
        }
        model.addAttribute("error", "Неверный логин или пароль");
        return "login";
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    public String login(@RequestParam String login,
                        @RequestParam String password,
                        HttpServletResponse response) {
        // Логика для проверки данных и авторизации пользователя
        boolean isAuthenticated = userService.authenticateUser(login, password, response);
        if (isAuthenticated) {
            return "redirect:/home"; // Переход на главную страницу после успешной авторизации
        } else {
            log.error("Неверный логин или пароль");
            return "login"; // В случае ошибки возвращаем на страницу логина
        }
    }

    // Страница регистрации
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public String register(@RequestParam String login,
                           @RequestParam String password,
                           @RequestParam String confirmPassword) {
        // Логика для регистрации пользователя
        boolean isRegistered = userService.registerUser(login, password);
        if (isRegistered) {
            return "redirect:/login"; // Переход на страницу логина после успешной регистрации
        } else {
            log.error("Ошибка регистрации");
            return "register"; // В случае ошибки возвращаем на страницу регистрации
        }
    }
}
