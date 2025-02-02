package ru.anikson.example.weatherapp.service.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.anikson.example.weatherapp.dao.SessionRepository;
import ru.anikson.example.weatherapp.dao.UserRepository;
import ru.anikson.example.weatherapp.entity.Session;
import ru.anikson.example.weatherapp.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final SessionRepository sessionRepository;

    @Override
    public boolean registerUser(String login,
                                String password) {

        User newUser = new User(
                null,
                login,
                password,
                null,
                null);
        userRepository.save(newUser);
        log.info("Юзер создан: {}", newUser);

        return true;
    }

    @Override
    public boolean authenticateUser(String login, String password, HttpServletResponse response) {
        Optional<User> user = userRepository.findByLogin(login);
        if (user.isEmpty()) {
            log.warn("Пользователь с логином {} не найден", login);
            return false;
        }

        if (!user.get().getPassword().equals(password)) {
            log.warn("Неверный пароль для пользователя {}", login);
            return false;
        }

        String sessionId = UUID.randomUUID().toString();
        Session newSession = new Session(
                null,
                sessionId,
                user.get(),
                LocalDateTime.now().plusDays(7)
        );
        sessionRepository.save(newSession);
        log.info("Сессия создана:{}", newSession);

        // Устанавливаем cookie с идентификатором сессии
        Cookie sessionCookie = new Cookie("sessionId", sessionId);
        sessionCookie.setHttpOnly(true);
        sessionCookie.setMaxAge(7 * 24 * 60 * 60);
        response.addCookie(sessionCookie);

        return true;
    }

    @Override
    public Optional<User> isUserAuthenticated(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("sessionId".equals(cookie.getName())) {
                    String sessionId = cookie.getValue();

                    // Ищем сессию по sessionId
                    Optional<Session> sessionOptional = sessionRepository.findBySessionId(sessionId);
                    if (sessionOptional.isPresent()) {
                        Session session = sessionOptional.get();

                        // Проверка, не истек ли срок действия сессии
                        if (session.getExpiresAt().isAfter(LocalDateTime.now())) {
                            // Ищем пользователя по ID, если сессия валидна
                            return Optional.of(userRepository.findById(session.getUserId().getId())
                                    .orElseThrow(() -> new RuntimeException("User not found")));
                        }
                    }
                }
            }
        }

        // Если сессия не найдена или она просрочена
        return Optional.empty();
    }
}
