package com.example.famz.services;

import com.example.famz.entity.Role;
import com.example.famz.entity.User;
import com.example.famz.repository.RoleRepository;
import com.example.famz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;

        this.roleRepository = roleRepository;
    }

    public User save(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encode password before saving
        // Fetch the default role from the database
        Role userRole = roleRepository.findByName("ROLE_USER");
        user.getRoles().add(userRole); // Assign the role to the user
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
