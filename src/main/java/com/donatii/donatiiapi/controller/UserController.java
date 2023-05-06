package com.donatii.donatiiapi.controller;

import com.donatii.donatiiapi.model.User;
import com.donatii.donatiiapi.service.UserService;
import com.donatii.donatiiapi.service.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.function.Predicate;

@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService service;

    @Autowired
    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User loginRequest) {
        try {
            User user = service.login(loginRequest.getEmail(), loginRequest.getParola());
            return ResponseEntity.ok().body(user);
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody User registerRequest) {
        try {
            service.register(registerRequest);
            return ResponseEntity.ok().body("User registered!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> update(@PathVariable("id") Long id, @RequestBody User user) {
        try {
            service.update(id, user);
            return ResponseEntity.ok().body("User updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Object> delete(@PathVariable("id") Long id) {
        try {
            service.delete(id);
            return ResponseEntity.ok().body("User deleted!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/like/{user_id}/{cauza_id}")
    public ResponseEntity<Object> like(@PathVariable("user_id") Long user_id, @PathVariable("cauza_id") Long cauza_id) throws NotFoundException {
        try {
            service.like(user_id, cauza_id);
            return ResponseEntity.ok().body("Like updated!");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
