package com.shop.website.controllers;

import com.shop.website.models.Role;
import com.shop.website.models.Users;
import com.shop.website.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserProfileController {
    @Autowired
    private UsersRepository usersRepository;
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping
    public String userList(Model model) {
        model.addAttribute("users", usersRepository.findAll());
        return "showUsers";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("{user}/edit")
    public String userEditPage(@PathVariable Users user, Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", Role.values());
        return "editUser";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
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
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{user}/remove")
    public String userBan(@PathVariable Users user) {
        usersRepository.delete(user);
        return "redirect:/user";
    }

    @GetMapping("/profile")
    public String userProfilePage(Users user, Model model) {
        model.addAttribute("user", user);
        return "showUserProfile";
    }

    @GetMapping("/profile/edit")
    public String userProfileEditPage(Users user, Model model) {
        model.addAttribute("user", user);
        return "editUserProfile";
    }
}
