package com.sanderik.services;

import com.sanderik.models.Role;
import com.sanderik.models.User;
import com.sanderik.repositories.RoleRepository;
import com.sanderik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashSet;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(new HashSet<>(Collections.singletonList(new Role("user"))));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String email) {
        return userRepository.findByEmail(email);
    }
}
