package com.shop.website.controllers;

import com.shop.website.models.Role;
import com.shop.website.models.Users;
import com.shop.website.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collections;
import java.util.Map;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UsersRepository usersRepository;

    @GetMapping("/signup")
    public String register(Model model) {
        model.addAttribute("title", "Register");
        return "register";
    }

    @PostMapping("/signup")
        public String userSignUp(Users user, Map<String, Object> model) {
        Users us = usersRepository.findByUsername(user.getUsername());

        if (us != null) {
            model.put("message", "User already exist!");
            return "register";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        user.setImage("https://upload.wikimedia.org/wikipedia/commons/9/99/Sample_User_Icon.png");
        usersRepository.save(user);

        return "redirect:/login";
    }
}