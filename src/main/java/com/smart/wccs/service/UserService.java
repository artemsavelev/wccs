package com.smart.wccs.service;

import com.smart.wccs.model.User;

import java.util.List;

public interface UserService {

    User register(User user);

    List<User> getAllUsers();

    User findByUsername(String username);

    User findById(Long id);

    void delete(Long id);

    void logout();
}
