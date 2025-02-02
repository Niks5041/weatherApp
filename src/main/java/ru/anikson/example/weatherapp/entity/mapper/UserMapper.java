package ru.anikson.example.weatherapp.entity.mapper;

import ru.anikson.example.weatherapp.entity.User;
import ru.anikson.example.weatherapp.entity.dto.UserDto;

public class UserMapper {

    public static UserDto toUserDto(User user) {
        return new UserDto(
                user.getLogin(),
                user.getPassword()
        );
    }
}
