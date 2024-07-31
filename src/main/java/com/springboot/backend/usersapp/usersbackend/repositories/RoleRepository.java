package com.springboot.backend.usersapp.usersbackend.repositories;

import com.springboot.backend.usersapp.usersbackend.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByName(String name);
}
