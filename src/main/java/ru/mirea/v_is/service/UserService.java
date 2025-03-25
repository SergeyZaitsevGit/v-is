package ru.mirea.v_is.service;

import ru.mirea.v_is.model.User;

public interface UserService {

    User saveUser(User user);

    User getAuthenticationUser();

    User getUserByEmail(String email);

}
