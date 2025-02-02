package ru.anikson.example.weatherapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anikson.example.weatherapp.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByLogin(String login);
}
