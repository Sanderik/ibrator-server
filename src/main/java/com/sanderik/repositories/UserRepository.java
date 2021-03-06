package com.sanderik.repositories;

import com.sanderik.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

    User findByEmail(String email);
}
