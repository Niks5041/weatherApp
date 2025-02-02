package ru.anikson.example.weatherapp.service.User;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ru.anikson.example.weatherapp.entity.User;

import java.util.Optional;

public interface UserService {

    boolean registerUser(String login,
                         String password);

    boolean authenticateUser(String login,
                             String password,
                             HttpServletResponse response);

    Optional<User> isUserAuthenticated(HttpServletRequest request);
}
