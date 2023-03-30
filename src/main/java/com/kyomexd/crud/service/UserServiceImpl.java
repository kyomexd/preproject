package com.kyomexd.crud.service;

import com.kyomexd.crud.model.Request;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.model.UserDTO;
import com.kyomexd.crud.repository.RequestRepository;
import com.kyomexd.crud.repository.RoleRepository;
import com.kyomexd.crud.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepositoryImpl userRepository;

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
    public void saveNewUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "ROLE_USER"));
        user.setRoles(roles);
        userRepository.addUser(user);
    }

    @Override
    public Set<Role> getDTORoles(UserDTO dto) {
        Set<Role> roles = new HashSet<>();
        if (dto.isHasUser()) {
            roles.add(new Role(1L, "ROLE_USER"));
        }
        if (dto.isHasAdmin()) {
            roles.add(new Role(2L, "ROLE_ADMIN"));
        }
        return roles;
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
    public User getUserByName(String name) {
        return userRepository.getUserByName(name);
    }

    @Override
    public void updateRequests(User user, Request request) {
        userRepository.updateRequests(user, request);
    }

    @Override
    public void addRole(int id, Role role) {
        userRepository.addRole(id, role);
    }
}
