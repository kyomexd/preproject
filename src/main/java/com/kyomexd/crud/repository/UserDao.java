package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAll();
    User findUserById(int id);
    void addUser(User user);
    void updateUser(int id, String name, int age, String email);
    void deleteUser(int id);
}
