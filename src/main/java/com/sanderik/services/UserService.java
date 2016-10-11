package com.sanderik.services;

import com.sanderik.models.User;

public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
