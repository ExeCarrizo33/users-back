package com.springboot.backend.usersapp.usersbackend.services;

import com.springboot.backend.usersapp.usersbackend.models.User;
import com.springboot.backend.usersapp.usersbackend.models.dto.UserDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> findAll();

    Page<User> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    User save(User user);

    Optional<User> update(UserDto user, Long id);

    void deleteById(Long id);
}
