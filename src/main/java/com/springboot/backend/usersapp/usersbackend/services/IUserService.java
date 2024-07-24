package com.springboot.backend.usersapp.usersbackend.services;

import com.springboot.backend.usersapp.usersbackend.models.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Optional<User> findById(Long id);

    User save(User user);

    void deleteById(Long id);
}
