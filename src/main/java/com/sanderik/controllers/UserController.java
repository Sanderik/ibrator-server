package com.sanderik.controllers;

import com.sanderik.dao.UserDao;
import com.sanderik.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserDao userDao;

    @RequestMapping("/user")
    public Iterable<User> get() {
        return userDao.findAll();
    }

    @RequestMapping("/user/{id}")
    public User getByEmail(@PathVariable("id") Long id) {
        return userDao.findOne(id);
    }
}
