package com.test.assignment.repositories;

import com.test.assignment.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long aLong);

    @Query("SELECT u FROM User u WHERE u.birthDate BETWEEN ?1 AND ?2")
    List<User> search(LocalDate from, LocalDate to);
}
