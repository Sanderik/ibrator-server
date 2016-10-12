package com.sanderik.repositories;

import com.sanderik.models.Role;
import com.sanderik.models.User;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<User, Long> {

    void save(Role role);
}
