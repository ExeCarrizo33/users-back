package com.springboot.backend.usersapp.usersbackend.repositories;

import com.springboot.backend.usersapp.usersbackend.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


public interface UserRepository extends JpaRepository<User, Long> {
}
