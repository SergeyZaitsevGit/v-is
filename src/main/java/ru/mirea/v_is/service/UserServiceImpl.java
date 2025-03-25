package ru.mirea.v_is.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.v_is.model.User;
import ru.mirea.v_is.repo.UserRepo;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepository;

    private BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    @Transactional
    public User saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isEmpty()) {
            user.setPassword(encoder().encode(user.getPassword()));
            user.setActivite(true);
            return userRepository.save(user);
        }
        return null;
    }

    @Override
    @Transactional
    public User getAuthenticationUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new AccessDeniedException("Пользователь не авторизован");
        }
        return getUserByEmail(
                authentication.getName()
        );
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("User not found")
        );
    }

}
