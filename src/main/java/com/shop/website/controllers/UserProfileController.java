package com.shop.website.controllers;

import com.shop.website.models.Role;
import com.shop.website.models.Users;
import com.shop.website.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserProfileController {
    @Autowired
    private UsersRepository usersRepository;
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", usersRepository.findAll());
        return "showUsers";
    }

    @GetMapping("{user}/edit")
    public String userEditPage(@PathVariable Users user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PostMapping("{user}/edit")
    public String userEdit(
            @PathVariable Users user,
            @RequestParam String username, @RequestParam String name,
            @RequestParam String image,
            @RequestParam Map<String, String> form)
    {
        user.setUsername(username);
        user.setName(name);
        user.setImage(image);

        Set<String> roles = Arrays.stream(Role.values())
                .map(Role::name)
                .collect(Collectors.toSet());

        user.getRoles().clear();

        for(String key : form.keySet()){
            if(roles.contains(key)){
                user.getRoles().add(Role.valueOf(key));
            }
        }

        usersRepository.save(user);
        return "redirect:/user";
    }
}
