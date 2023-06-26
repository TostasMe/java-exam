package com.shop.website.controllers;

import com.shop.website.models.Goods;
import com.shop.website.models.Users;
import com.shop.website.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {
    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("/")
    public String home(Model model, @AuthenticationPrincipal Users user) {
        if(user != null) {
            model.addAttribute("username", user.getUsername());
        }

        Iterable<Goods> goods = goodsRepository.findAll();
        model.addAttribute("goods", goods);
        return "home";
    }
}
