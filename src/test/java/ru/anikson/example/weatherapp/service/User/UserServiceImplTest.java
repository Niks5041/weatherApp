package ru.anikson.example.weatherapp.service.User;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.anikson.example.weatherapp.dao.SessionRepository;
import ru.anikson.example.weatherapp.dao.UserRepository;
import ru.anikson.example.weatherapp.entity.Session;
import ru.anikson.example.weatherapp.entity.User;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private UserServiceImpl userService;

    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User(1, "testUser", "password", null, null);
    }

    @Test
    void testRegisterUser() {
        when(userRepository.save(any(User.class))).thenReturn(testUser);

        boolean result = userService.registerUser("testUser", "password");

        assertTrue(result);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void testAuthenticateUser_Success() {
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(testUser));

        boolean result = userService.authenticateUser("testUser", "password", response);

        assertTrue(result);
        verify(sessionRepository, times(1)).save(any(Session.class));
    }

    @Test
    void testAuthenticateUser_Fail_WrongPassword() {
        when(userRepository.findByLogin("testUser")).thenReturn(Optional.of(testUser));

        boolean result = userService.authenticateUser("testUser", "wrongPassword", response);

        assertFalse(result);
    }

    @Test
    void testIsUserAuthenticated_Success() {
        String sessionId = UUID.randomUUID().toString();
        Session session = new Session(1, sessionId, testUser, LocalDateTime.now().plusDays(7));

        Cookie cookie = new Cookie("sessionId", sessionId);
        HttpServletRequest request = mock(HttpServletRequest.class);

        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(sessionRepository.findBySessionId(sessionId)).thenReturn(Optional.of(session));
        when(userRepository.findById(1)).thenReturn(Optional.of(testUser));

        Optional<User> authenticatedUser = userService.isUserAuthenticated(request);

        assertTrue(authenticatedUser.isPresent());
        assertEquals("testUser", authenticatedUser.get().getLogin());
    }
}