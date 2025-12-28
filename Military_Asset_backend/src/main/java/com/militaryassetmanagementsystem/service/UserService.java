package com.militaryassetmanagementsystem.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.militaryassetmanagementsystem.model.User;
import com.militaryassetmanagementsystem.repo.UserRepository;

@Service

public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(password)) {
                return user; // return the User object itself
            }
        }
        return null; // login failed
    }
}
