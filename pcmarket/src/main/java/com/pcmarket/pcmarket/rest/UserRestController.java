package com.pcmarket.pcmarket.rest;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcmarket.pcmarket.dto.LoginRequest;
import com.pcmarket.pcmarket.entity.User;
import com.pcmarket.pcmarket.security.JwtService;
import com.pcmarket.pcmarket.service.UserService;

@CrossOrigin(origins = "http://localhost:4200/") // Allows Angular frontend to access this API
@RestController
@RequestMapping("/api/users")
public class UserRestController {
    private UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // Post method to create a new user
    @PostMapping
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
    }

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequest request) {
        System.out.println("Login attempt for user: " + request.getUsername());

        User user = userService.authenticate(request.getUsername(), request.getPassword());

        // Generate JWT token
        JwtService jwtService = new JwtService();
        String token = jwtService.generateToken(user.getUsername());

        // Return JSON body containing the token
        Map<String, String> body = Map.of("token", token, "userId", String.valueOf(user.getId()));
        return ResponseEntity.ok(body);
    }

    // Get method to find a user by its ID
    @GetMapping("/{id}")
    public User getUserById(@PathVariable int id) {
        return userService.getUserById(id);
    }

    // Get method to list all users
    @GetMapping("/userList")
    public List<User> getUserList() {
        return userService.getAllUsers();
    }

    // Put method to update an existing user by its ID
    @PatchMapping("/{id}")
    public ResponseEntity<User> patchUser(@PathVariable int id, @RequestBody Map<String, Object> updates) {
        User updatedUser = userService.patchUser(id, updates);
        return ResponseEntity.ok(updatedUser);
    }

    // Delete method to delete a user by its ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
