package ru.anikson.example.weatherapp.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.anikson.example.weatherapp.entity.Location;

import java.util.List;

public interface LocationRepository extends JpaRepository<Location, Integer> {
    List<Location> findAllById(Integer userId);
}
