package com.shop.website.controllers;

import com.shop.website.models.Goods;
import com.shop.website.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsRepository goodsRepository;

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/add")
    public String addGoodsPage() {

        return "addGood";
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/add")
    public String addGoods(Goods good) {
        goodsRepository.save(good);
        return "redirect:/goods/add";
    }

    @GetMapping("/{good}")
    public String goodsPage(@PathVariable Goods good, Model model) {
        model.addAttribute("good", good);
        return "showGoods";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/{good}/edit")
    public String goodsEditPage(@PathVariable Goods good, Model model){
        model.addAttribute("good", good);
        return "editGood";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{good}/edit")
    public String goodsEdit(@PathVariable Goods good, @RequestParam String name, @RequestParam String brand,
                            @RequestParam String image, @RequestParam Double price){
        good.setName(name);
        good.setBrand(brand);
        good.setImage(image);
        good.setPrice(price);

        goodsRepository.save(good);
        return "redirect:/goods/{good}";
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/{good}/remove")
    public String goodsRemove(@PathVariable Goods good) {
        goodsRepository.delete(good);
        return "redirect:/";
    }
}
