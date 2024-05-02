package com.test.assignment.services;

import com.test.assignment.entities.User;
import com.test.assignment.exceptions.UserNotFoundException;
import com.test.assignment.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;
    @Value("${test.assignment.myproperty}")
    private long yearInSeconds;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> createNewUser(User user) {
        long differenceSeconds = getDifferenceSeconds(user);

        if (differenceSeconds > yearInSeconds) {
            userRepository.save(user);
            return new ResponseEntity<>("Created new user", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("User is not 18 years old to register", HttpStatus.OK);
        }
    }

    public ResponseEntity<String> updatePartialUser(User updatedUser, long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }
        if (updatedUser.getFirstName() != null) {
            user.setFirstName(updatedUser.getFirstName());
        }
        if (updatedUser.getLastName() != null) {
            user.setLastName(updatedUser.getLastName());
        }
        if (updatedUser.getBirthDate() != null) {
            user.setBirthDate(updatedUser.getBirthDate());
        }
        if (updatedUser.getAddress() != null) {
            user.setAddress(updatedUser.getAddress());
        }
        if (updatedUser.getNumber() != null) {
            user.setNumber(updatedUser.getNumber());
        }
        userRepository.save(user);
        return new ResponseEntity<>("User from id: " + id + " updated", HttpStatus.OK);
    }

    public ResponseEntity<String> updateFullUser(User updatedUser, long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        user.setEmail(updatedUser.getEmail());
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setBirthDate(updatedUser.getBirthDate());
        user.setAddress(updatedUser.getAddress());
        user.setNumber(updatedUser.getNumber());
        userRepository.save(user);
        return new ResponseEntity<>("User from id: " + id + " updated",HttpStatus.OK);
    }

    public ResponseEntity<String> deleteUserById(long id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
        userRepository.deleteById(user.getId());
        return new ResponseEntity<>("User is deleted",HttpStatus.OK);
    }

    public List<User> search(LocalDate from, LocalDate to) {
        return userRepository.search(from, to);
    }

    private long getDifferenceSeconds(User user) {
        LocalDateTime now = LocalDateTime.now();
        long nowSeconds = now.toEpochSecond(ZoneOffset.UTC);
        LocalDate birthDate = user.getBirthDate();

        long birthDateSeconds = birthDate.atStartOfDay().toEpochSecond(ZoneOffset.UTC);

        return nowSeconds - birthDateSeconds;
    }
}
