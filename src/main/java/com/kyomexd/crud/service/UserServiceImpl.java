package com.kyomexd.crud.service;

import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.repository.RoleRepository;
import com.kyomexd.crud.repository.UserRepositoryImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    @Autowired
    private UserRepositoryImpl userRepository;

    @Autowired
    private RoleRepository roleRepository;

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
        userRepository.addUser(user);
    }

    @Override
    public void updateUser(int id, String name, int age, String email, Set<Role> roles) {
        userRepository.updateUser(id, name, age, email, roles);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return userRepository.getUserByName(s);
    }
}
