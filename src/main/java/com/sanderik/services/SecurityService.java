package com.sanderik.services;

public interface SecurityService {

    String findLoggedInemail();

    void autoLogin(String email, String password);
}
