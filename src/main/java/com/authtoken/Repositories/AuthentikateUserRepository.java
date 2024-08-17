package com.authtoken.Repositories;

import com.authtoken.Models.AuthentikateUsers;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface AuthentikateUserRepository extends CrudRepository<AuthentikateUsers, String> {
    List<AuthentikateUsers> findByUserEmail(String userEmail);
}
