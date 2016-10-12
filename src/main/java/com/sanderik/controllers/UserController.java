package com.sanderik.controllers;

import com.sanderik.models.User;
import com.sanderik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired private UserRepository userDao;

    @RequestMapping("/user")
    public Iterable<User> get() {
        return userDao.findAll();
    }

    @RequestMapping("/user/{id}")
    public User getByEmail(@PathVariable("id") Long id) {
        return userDao.findOne(id);
    }
}
