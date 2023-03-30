package com.kyomexd.crud.controller;

import com.kyomexd.crud.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @GetMapping
    public String getAllUsers(Model model, Authentication auth) {
        model.addAttribute("name", auth.getName());
        model.addAttribute("roles", auth.getAuthorities());
        model.addAttribute("user", new User());
        return "admin";
    }
}
