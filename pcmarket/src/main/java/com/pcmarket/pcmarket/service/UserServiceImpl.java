package com.pcmarket.pcmarket.service;

import java.util.List;
import java.util.Map;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.pcmarket.pcmarket.dao.UserRepository;
import com.pcmarket.pcmarket.entity.User;
import com.pcmarket.pcmarket.security.JwtService;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Override
    public void createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(int id, User user) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAddress1(user.getAddress1());
        existingUser.setAddress2(user.getAddress2());
        existingUser.setZipCode(user.getZipCode());
        existingUser.setStateCode(user.getStateCode());
        existingUser.setCountryCode(user.getCountryCode());

        return userRepository.save(existingUser);
    }

    @Override
    public User patchUser(int id, Map<String, Object> updates) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "firstName" -> existingUser.setFirstName((String) value);
                case "lastName" -> existingUser.setLastName((String) value);
                case "address1" -> existingUser.setAddress1((String) value);
                case "address2" -> existingUser.setAddress2((String) value);
                case "stateCode" -> existingUser.setStateCode((String) value);
                case "zipCode" -> existingUser.setZipCode((String) value);
                case "countryCode" -> existingUser.setCountryCode((String) value);
                default -> {
                } // ignore uneditable fields like username, dob, etc.
            }
        });

        return userRepository.saveAndFlush(existingUser);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteById(id);
    }

    @Override
    public User authenticate(String username, String password) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Invalid username or password"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            System.out.println("Password does NOT match");
            throw new RuntimeException("INVALID PASSWORD!");
        } else {
            System.out.println("Password matches");
        }

        // Generate JWT and attach to user (or return separately)
        String token = jwtService.generateToken(username);
        System.out.println("Generated Token: " + token);

        return user;
    }
}
