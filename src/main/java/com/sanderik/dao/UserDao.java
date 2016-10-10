package com.sanderik.dao;

import com.sanderik.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserDao extends CrudRepository<User, Long> {

    List<User> findByEmail(String email);
}
