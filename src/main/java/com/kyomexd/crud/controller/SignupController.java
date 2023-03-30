package com.kyomexd.crud.controller;

import com.kyomexd.crud.model.Role;
import com.kyomexd.crud.model.User;
import com.kyomexd.crud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashSet;
import java.util.Set;

@Controller
@RequestMapping("/signup")
public class SignupController {
    @Autowired
    UserService userService;

    @GetMapping()
    public String signupUser(Model model) {
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping()
    public String saveUser(@ModelAttribute("user") @Validated User user, BindingResult result, Model model) {
        if (result.hasErrors()) {
            user.setPassword("");
            model.addAttribute("errorMessage", "Bad credentials. Please try again");
            return "signup";
        }
        userService.saveNewUser(user);
        return "redirect:/user";
    }
}
