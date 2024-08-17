package com.authtoken.Controllers;

import com.authtoken.Models.AuthentikateUsers;
import com.authtoken.Repositories.AuthentikateUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class AuthentikateUserController {
    @Autowired
    AuthentikateUserRepository authentikateUserRepository;

    @GetMapping("/getAll")
    public ResponseEntity<List<AuthentikateUsers>> getAllUsers() {
        List<AuthentikateUsers> authentikateUsersList = (List<AuthentikateUsers>) authentikateUserRepository.findAll();
        return ResponseEntity.status(HttpStatus.OK).body(authentikateUsersList);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthentikateUsers> registerUser(@RequestParam("userName") String userName, @RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword) {
        AuthentikateUsers authentikateUser = new AuthentikateUsers();
        authentikateUser.setUserName(userName);
        authentikateUser.setUserEmail(userEmail);
        authentikateUser.setUserPassword(userPassword);
        authentikateUserRepository.save(authentikateUser);
        return ResponseEntity.status(HttpStatus.OK).body(authentikateUser);
    }

    @PostMapping("/signIn")
    public ResponseEntity<AuthentikateUsers> signInUser(@RequestParam("userEmail") String userEmail, @RequestParam("userPassword") String userPassword) {
        AuthentikateUsers signInUser = authentikateUserRepository.findByUserEmail(userEmail).getFirst();
        if (signInUser.getUserEmail().equals(userEmail) && userPassword.equals(signInUser.getUserPassword())) {
            return ResponseEntity.status(HttpStatus.OK).body(signInUser);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
