package com.kyomexd.crud.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.model.UserProfile;
import com.kyomexd.crud.repository.RoleRepository;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping
    public String getAllUsers(Model model, Authentication auth) {
        model.addAttribute("name", auth.getName());
        model.addAttribute("roles", auth.getAuthorities());
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("user", new User());
        return "admin";
    }

    @GetMapping("/table")
    @ResponseBody
    public List<User> getUsersTable() {
        return userService.getAllUsers();
    }

    @GetMapping("/add")
    public String signupUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "add";
    }

    @PostMapping("/add")
    @ResponseStatus(value = HttpStatus.OK)
    public void saveUser(@RequestBody String userJson, BindingResult result) throws JsonProcessingException {
        UserProfile profile = new ObjectMapper().readValue(userJson, UserProfile.class);
        Set<Role> roles = new HashSet<>();
        if (profile.isHasUser()) {
            roles.add(new Role(1L, "ROLE_USER"));
        }
        if (profile.isHasAdmin()) {
            roles.add(new Role(2L, "ROLE_ADMIN"));
        }
        userService.saveUser(new User(profile.getName(), profile.getAge(), profile.getEmail(), profile.getPassword(), roles));
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @ResponseBody
    @GetMapping("/edit/{id}")
    public User editUser(@PathVariable int id) {
        return userService.getUserById(id);
    }

    @ResponseStatus(value = HttpStatus.OK)
    @PostMapping("/edit/{id}")
    public void saveEditedUser(@RequestBody String userJson) throws JsonProcessingException {
        UserProfile profile = new ObjectMapper().readValue(userJson, UserProfile.class);
        Set<Role> roles = new HashSet<>();
        if (profile.isHasUser()) {
            roles.add(new Role(1L, "ROLE_USER"));
        }
        if (profile.isHasAdmin()) {
            roles.add(new Role(2L, "ROLE_ADMIN"));
        }
        userService.updateUser(profile.getId(), profile.getName(), profile.getAge(), profile.getEmail(), roles);
    }
}
