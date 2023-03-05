package com.kyomexd.crud.service;

import com.kyomexd.crud.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User findUserById(int id);
    void saveUser(User user);
    void updateUser(int id, String name, int age, String email);
    void deleteUser(int id);
}
