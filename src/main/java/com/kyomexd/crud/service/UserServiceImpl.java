package com.kyomexd.crud.service;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.repository.RequestRepository;
import com.kyomexd.crud.repository.RoleRepository;
import com.kyomexd.crud.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.getUserById(id);
    }

    @Override
    public void saveUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.addUser(user);
    }

    @Override
    public void updateUser(int id, String name, String age, String email, String city, Set<Role> roles) {
        userRepository.updateUser(id, name, age, email, city, roles);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.getUserByName(s);
    }

    @Override
    public List<Request> getAllRequests() {
        return requestRepository.findAll();
    }

    @Override
    public void saveRequest(Request request) {
        requestRepository.saveRequest(request);
    }
    @Override
    public void resolveRequest(long id) {
        requestRepository.resolveRequest(id);
    }
    @Override
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    public void updateRequests(User user, Request request) {
        userRepository.updateRequests(user, request);
    }
}
