package ru.anikson.example.weatherapp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    @Email(message = "Неверный формат email")
    private String login;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "userId")
    private Set<Location> locations;  // Связь с Location

    @OneToMany(mappedBy = "userId")
    private Set<Session> sessions;  // Связь с Session
}
