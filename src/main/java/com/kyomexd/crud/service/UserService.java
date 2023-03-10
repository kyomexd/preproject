package com.kyomexd.crud.service;

import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;

import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(int id);
    void saveUser(User user);
    void updateUser(int id, String name, int age, String email, Set<Role> roles);
    void deleteUser(int id);
}
