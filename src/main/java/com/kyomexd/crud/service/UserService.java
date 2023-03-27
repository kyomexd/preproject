package com.kyomexd.crud.service;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface UserService {

    List<User> getAllUsers();
    User getUserById(int id);
    void saveUser(User user);
    void updateUser(int id, String name, String age, String email, String city, Set<Role> roles);
    void deleteUser(int id);
    List<Request> getAllRequests();
    void saveRequest(Request request);
    void resolveRequest(long id);
    User getUserByName(String name);
    void updateRequests(User user, Request request);
}
