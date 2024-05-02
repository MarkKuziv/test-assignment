package com.test.assignment;

import com.test.assignment.entities.User;
import com.test.assignment.exceptions.UserNotFoundException;
import com.test.assignment.repositories.UserRepository;
import com.test.assignment.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTests {
    @Mock
    private User user;
    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @Test
    public void itCreatesUser() {
        userService = new UserService(userRepository);
        when(user.getBirthDate()).thenReturn(LocalDate.of(2004, 5, 19));
        userService.createNewUser(user);
        verify(userRepository).save(user);
    }

    @Test
    public void itDeletesUser() {
        userService = new UserService(userRepository);
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        when(user.getId()).thenReturn(1L);
        userService.deleteUserById(1L);
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void itUpdatesUser() {
        userService = new UserService(userRepository);
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        userService.updateFullUser(user, 1L);
        verify(userRepository).save(user);
    }

    @Test
    public void itUpdatesPartialUser() {
        userService = new UserService(userRepository);
        when(userRepository.findUserById(1L)).thenReturn(Optional.of(user));
        userService.updatePartialUser(user, 1L);
        verify(userRepository).save(user);
    }

    @Test
    public void itThrowsWhenUserNotFoundUpdatesPartialUser() {
        userService = new UserService(userRepository);
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.updatePartialUser(user, 1L))
                .hasMessage("User not found")
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void itThrowsWhenUserNotFoundUpdatesFullUser() {
        userService = new UserService(userRepository);
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.updateFullUser(user, 1L))
                .hasMessage("User not found")
                .isInstanceOf(UserNotFoundException.class);
    }

    @Test
    public void itThrowsWhenUserNotFoundDeletesUser() {
        userService = new UserService(userRepository);
        when(userRepository.findUserById(1L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> userService.deleteUserById(1L))
                .hasMessage("User not found")
                .isInstanceOf(UserNotFoundException.class);
    }
}
