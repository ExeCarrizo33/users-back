package com.springboot.backend.usersapp.usersbackend.controllers;

import com.springboot.backend.usersapp.usersbackend.models.User;
import com.springboot.backend.usersapp.usersbackend.services.impl.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/users", produces = "application/json")
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @GetMapping
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/page/{page}")
    public Page<User> listPageable(@PathVariable Integer page) {
        Pageable pageable = PageRequest.of(page,4);
        return userService.findAll(pageable);
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
    public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }
        return ResponseEntity.ok(userService.save(user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody User user, BindingResult result) {
        if (result.hasErrors()) {
            return validation(result);
        }

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


    private static ResponseEntity<?> validation(BindingResult result) {
        Map<String, String> errors = new HashMap<>();
        result.getFieldErrors().forEach(error -> errors.put(error.getField(),"El campo " + error.getField() + " "+ error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors);
    }
}
