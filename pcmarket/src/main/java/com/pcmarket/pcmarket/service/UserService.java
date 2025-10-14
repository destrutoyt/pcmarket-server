package com.pcmarket.pcmarket.service;

import java.util.List;

import com.pcmarket.pcmarket.entity.User;

public interface UserService {
    void createUser(User user);

    User authenticate(String username, String password);

    User getUserById(int id);

    List<User> getAllUsers();

    User updateUser(int id, User user);

    void deleteUser(int id);
}
