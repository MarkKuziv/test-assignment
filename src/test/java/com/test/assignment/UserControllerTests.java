package com.test.assignment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.assignment.controllers.UserController;
import com.test.assignment.entities.User;
import com.test.assignment.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTests {
    @Mock
    private UserService userService;
    @Autowired
    private ObjectMapper objectMapper;
    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    public void isCreatesUser() {
        User newUser = new User();
        when(userService.createNewUser(newUser)).thenReturn(new ResponseEntity<>("User created successfully", HttpStatus.CREATED));

        UserController userController = new UserController(userService);
        ResponseEntity<String> response = userController.createUser(newUser);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User created successfully", response.getBody());

        verify(userService, times(1)).createNewUser(newUser);
    }

    @Test
    public void itDeletesUser() {
        long id = 1;
        when(userService.deleteUserById(id)).thenReturn(new ResponseEntity<>("User deleted successfully", HttpStatus.OK));

        UserController userController = new UserController(userService);
        ResponseEntity<String> response = userController.deleteUser(id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User deleted successfully", response.getBody());

        verify(userService).deleteUserById(id);
    }

    @Test
    public void itUpdatesFullUser() {
        User updatedUser = new User();
        long id = 1;
        when(userService.updateFullUser(updatedUser, id)).thenReturn(new ResponseEntity<>("User updated successfully", HttpStatus.OK));

        UserController userController = new UserController(userService);
        ResponseEntity<String> response = userController.updateFullUser(id, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User updated successfully", response.getBody());

        verify(userService).updateFullUser(updatedUser, id);
    }

    @Test
    public void itUpdatesPartialUser() {
        User updatedUser = new User();
        long id = 1;
        when(userService.updatePartialUser(updatedUser, id)).thenReturn(new ResponseEntity<>("User partially updated", HttpStatus.OK));

        UserController userController = new UserController(userService);
        ResponseEntity<String> response = userController.updatePartialUser(id, updatedUser);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("User partially updated", response.getBody());

        verify(userService).updatePartialUser(updatedUser, id);
    }
}

