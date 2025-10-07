package com.pcmarket.pcmarket.rest;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pcmarket.pcmarket.entity.User;
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
    @PostMapping("/create")
    public void createUser(@RequestBody User user) {
        userService.createUser(user);
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

    // Post method to update an existing user by its ID
    @PostMapping("/{id}")
    public void updateUser(@PathVariable int id, @RequestBody User user) {
        userService.updateUser(id, user);
    }

    // Delete method to delete a user by its ID
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }
}
