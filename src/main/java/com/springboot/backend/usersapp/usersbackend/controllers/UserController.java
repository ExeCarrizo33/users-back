package com.springboot.backend.usersapp.usersbackend.controllers;

import com.springboot.backend.usersapp.usersbackend.models.User;
import com.springboot.backend.usersapp.usersbackend.services.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> showById(@PathVariable Long id) {
        Optional<User> userList = userService.findById(id);
        if (userList.isPresent()) {
            return ResponseEntity.ok(userList.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/save")
    public ResponseEntity<User> create(@RequestBody User user) {
        return ResponseEntity.ok(userService.save(user));
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> update(@PathVariable Long id, @RequestBody User user) {
        Optional<User> userList = userService.findById(id);
        if (userList.isPresent()) {
            User userDB = userList.get();
            userDB.setName(user.getName());
            userDB.setLastname(user.getLastname());
            userDB.setEmail(user.getEmail());
            userDB.setUsername(user.getUsername());
            userDB.setPassword(user.getPassword());
            return ResponseEntity.ok(userService.save(userDB));
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        Optional<User> userList = userService.findById(id);
        if (userList.isPresent()) {
            userService.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
