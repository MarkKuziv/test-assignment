package com.test.assignment.controllers;

import com.test.assignment.entities.User;
import com.test.assignment.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        return userService.createNewUser(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<String> updatePartialUser(@PathVariable long id, @RequestBody User updatedUser) {
        return userService.updatePartialUser(updatedUser, id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateFullUser(@PathVariable long id, @RequestBody User updatedUser) {
        return userService.updateFullUser(updatedUser, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable long id) {
        return userService.deleteUserById(id);
    }

    @PostMapping("/search")
    public ResponseEntity<List<User>> search(@RequestParam("from") LocalDate from, @RequestParam("to") LocalDate to) {
        List<User> users = userService.search(from, to);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

}

