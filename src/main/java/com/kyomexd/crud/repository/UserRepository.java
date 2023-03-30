package com.kyomexd.crud.repository;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface UserRepository {

    List<User> findAll();
    User getUserById(int id);
    void addUser(User user);
    void updateUser(int id, String name, String age, String email, String city, Set<Role> roles);
    void deleteUser(int id);
    void updateRequests(User user, Request request);
    void addRole(int id, Role role);
}
