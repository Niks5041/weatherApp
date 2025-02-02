package ru.anikson.example.weatherapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anikson.example.weatherapp.entity.Session;

import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {
    Optional<Session> findBySessionId(String sessionId);
}
