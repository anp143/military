package com.militaryassetmanagementsystem.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import com.militaryassetmanagementsystem.model.User;
import com.militaryassetmanagementsystem.service.UserService;

@RestController
@RequestMapping("/api/User")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> userMap) {
        String username = userMap.get("username");
        String password = userMap.get("password");

        User user = userService.login(username, password);
        if (user != null) {
            Map<String, Object> response = new HashMap<>();
            response.put("message", "Login successful");
            response.put("role", user.getRole());
            if (user.getBase() != null) {
                response.put("baseId", user.getBase().getId());
                response.put("baseName", user.getBase().getName());
            } else {
                response.put("baseId", null);
                response.put("baseName", null);
            }
            return ResponseEntity.ok(response);
        }

        return ResponseEntity.status(401).body("Invalid username or password");
    }

}
