package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;

import java.util.List;
import java.util.Set;

public interface UserRepository {

    List<User> findAll();
    User getUserById(int id);
    void addUser(User user);
    void updateUser(int id, String name, int age, String email, Set<Role> roles);
    void deleteUser(int id);
}
