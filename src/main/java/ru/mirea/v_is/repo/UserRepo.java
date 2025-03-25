package ru.mirea.v_is.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.v_is.model.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
