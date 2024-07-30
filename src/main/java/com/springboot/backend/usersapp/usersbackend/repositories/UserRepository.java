package com.springboot.backend.usersapp.usersbackend.repositories;

import com.springboot.backend.usersapp.usersbackend.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

    Page<User> findAll(Pageable pageable);

}
