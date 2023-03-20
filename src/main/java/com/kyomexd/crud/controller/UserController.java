package com.kyomexd.crud.controller;

import com.kyomexd.crud.config.SecurityConfig;
import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/signup")
    public String signupUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }
    @PostMapping("/signup")
    public String saveUser(@ModelAttribute("user") @Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setPassword("");
            model.addAttribute("errorMessage", "Bad credentials. Please try again");
            return "signup";
        }
        Set<Role> roles = new HashSet<>();
        roles.add(new Role(1L, "ROLE_USER"));
        user.setRoles(roles);
        try {
            userService.saveUser(user);
        } catch (RuntimeException e) {
            model.addAttribute("errorMessage",
                    "User with these credentials already exists. Please choose other name/email");
            user.setPassword("");
            return "signup";
        }
        return "redirect:/user";
    }

    @GetMapping("/user")
    public String showUserpage(Model model, Authentication auth) {
        model.addAttribute("user", auth.getPrincipal());
        model.addAttribute("roles", auth.getAuthorities());
        return "/user";
    }
}
