package com.springboot.backend.usersapp.usersbackend.services.impl;


import com.springboot.backend.usersapp.usersbackend.models.Role;
import com.springboot.backend.usersapp.usersbackend.models.User;
import com.springboot.backend.usersapp.usersbackend.models.dto.IUser;
import com.springboot.backend.usersapp.usersbackend.models.dto.UserDto;
import com.springboot.backend.usersapp.usersbackend.repositories.RoleRepository;
import com.springboot.backend.usersapp.usersbackend.repositories.UserRepository;
import com.springboot.backend.usersapp.usersbackend.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        return this.userRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<User> findAll(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long id) {
        return this.userRepository.findById(id);
    }

    @Override
    @Transactional
    public User save(User user) {
        user.setRoles(getRoles(user));
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return this.userRepository.save(user);
    }


    @Override
    @Transactional
    public Optional<User> update(UserDto user, Long id) {

        Optional<User> userList = userRepository.findById(id);

        if (userList.isPresent()) {
            User userDB = userList.get();
            userDB.setName(user.getName());
            userDB.setLastname(user.getLastname());
            userDB.setEmail(user.getEmail());
            userDB.setUsername(user.getUsername());


            userDB.setRoles(getRoles(user));
            return Optional.of(userRepository.save(userDB));
        }

        return Optional.empty();
    }

    @Override
    @Transactional
    public void deleteById(Long id) {
        this.userRepository.deleteById(id);
    }

    private List<Role> getRoles(IUser user) {
        List<Role> roles = new ArrayList<>();
        Optional<Role> optionalRole = roleRepository.findByName("ROLE_USER");
        optionalRole.ifPresent(roles::add);

        if(user.isAdmin()) {
            Optional<Role> optionalRoleAdmin = roleRepository.findByName("ROLE_ADMIN");
            optionalRoleAdmin.ifPresent(roles::add);
        }
        return roles;
    }
}
