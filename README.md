# Проект "Погода" ⛅

## Описание
Веб-приложение для просмотра текущей погоды. Пользователь может зарегистрироваться, авторизоваться и добавить в коллекцию одну или несколько локаций, после чего главная страница приложения начинает отображать список локаций с их текущей погодой.

## Технологии
- **Java 17**
- **Spring Boot** (Spring MVC, Spring Data JPA)
- **Thymeleaf**
- **PostgreSQL**
- **Flyway** (для миграций)
- **OpenWeatherMap API** (для получения данных о погоде)
- **Lombok**
- **JUnit, Mockito** (для тестирования)

## Функциональность
- Регистрация и авторизация пользователей
- Добавление и просмотр сохранённых локаций
- Получение и обновление данных о погоде в реальном времени
- Управление сессиями и аутентификация через cookies
