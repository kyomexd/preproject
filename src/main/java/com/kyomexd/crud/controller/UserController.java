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
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

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
    public String saveUser(@ModelAttribute("user") @Validated User user) {
        user.setRoles(Collections.singleton(new Role(1L, "ROLE_USER")));
        userService.saveUser(user);
        return "redirect:/user";
    }

    @GetMapping("/user")
    public String showUserpage(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = (User) auth.getPrincipal();
        model.addAttribute("user", user);
        return "/user";
    }
}
