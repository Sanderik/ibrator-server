package com.sanderik.controllers;

import com.sanderik.models.User;
import com.sanderik.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

@Controller
abstract class BaseController {

    @Autowired protected UserRepository userRepository;

    public User getUser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(auth.getName());
    }
}
