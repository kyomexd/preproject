package com.kyomexd.crud.controller;

import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.repository.RoleRepository;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
    public String getAllUsers(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin";
    }

    @GetMapping("/add")
    public String signupUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roles", roleRepository.findAll());
        return "add";
    }

    @PostMapping("/add")
    public String saveUser(@ModelAttribute("user") @Validated User user,
                           @RequestParam(defaultValue = "false") boolean role_user,
                           @RequestParam(defaultValue = "false") boolean role_admin) {
        Set<Role> roles = new HashSet<>();
        if (role_user) {
            roles.add(new Role(1L, "ROLE_USER"));
        }
        if (role_admin) {
            roles.add(new Role(2L, "ROLE_ADMIN"));
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return "redirect:/admin";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable int id, Model model) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/edit/{id}")
    public String editUser(@PathVariable int id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roles", roleRepository.findAll());
        return "edit";
    }

    @PostMapping("/edit/{id}")
    public String saveEditedUser(@ModelAttribute("user") User user,
                                 @PathVariable int id,
                                 @RequestParam(defaultValue = "false") boolean role_user,
                                 @RequestParam(defaultValue = "false") boolean role_admin) {
        Set<Role> roles = new HashSet<>();
        if (role_user) {
            roles.add(new Role(1L, "ROLE_USER"));
        }
        if (role_admin) {
            roles.add(new Role(2L, "ROLE_ADMIN"));
        }
        if (!role_user && !role_admin) {
            roles = userService.getUserById(id).getRoles();
        }
        userService.updateUser(id, user.getName(), user.getAge(), user.getEmail(), roles);
        return "redirect:/admin";
    }
}
