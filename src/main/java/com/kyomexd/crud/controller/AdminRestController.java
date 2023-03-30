package com.kyomexd.crud.controller;

import com.kyomexd.crud.model.*;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    @Autowired
    private UserService userService;

    @GetMapping("/table")
    public List<User> getUsersTable() {
        return userService.getAllUsers();
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveUser(@RequestBody UserDTO profile) {
        userService.saveUser(new User(profile.getName(), profile.getAge(), profile.getEmail(), profile.getPassword(), profile.getCity(), userService.getDTORoles(profile)));
    }

    @GetMapping("/delete/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
    }

    @GetMapping("/edit/{id}")
    public User editUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @PostMapping("/edit/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveEditedUser(@RequestBody UserDTO profile) {
        userService.updateUser(profile.getId(), profile.getName(), profile.getAge(), profile.getEmail(), profile.getCity(), userService.getDTORoles(profile));
    }
}
