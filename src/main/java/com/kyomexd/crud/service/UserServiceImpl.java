package com.kyomexd.crud.service;

import com.kyomexd.crud.model.User;
import com.kyomexd.crud.repository.UserDaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDaoRepository userRepository;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findUserById(int id) {
        return userRepository.findUserById(id);
    }

    @Override
    public void saveUser(User user) {
        userRepository.addUser(user);
    }

    @Override
    public void updateUser(int id, String name, int age, String email) {
        userRepository.updateUser(id, name, age, email);
    }

    @Override
    public void deleteUser(int id) {
        userRepository.deleteUser(id);
    }
}
