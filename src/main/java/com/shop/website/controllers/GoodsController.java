package com.shop.website.controllers;

import com.shop.website.models.Goods;
import com.shop.website.models.Users;
import com.shop.website.repository.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class GoodsController {
    @Autowired
    private GoodsRepository goodsRepository;

    @GetMapping("/goods/add")
    public String addGoodsPage(Model model){
        model.addAttribute("title", "Admin Panel");
        return "addGoods";
    }

    @PostMapping("/goods/add")
    public String addGoods(@RequestParam String name, @RequestParam String brand,
                           @RequestParam String image, @RequestParam Double price, Model model){

        Goods good = new Goods(name, brand, image, price);
        goodsRepository.save(good);

        return "redirect:/goods/add";
    }

    @GetMapping("/goods/{id}")
    public String goodsPage(@PathVariable(value = "id") long id, Model model){
        if(!goodsRepository.existsById(id))
        {
            return "redirect:/";
        }
        Optional<Goods> good = goodsRepository.findById(id);
        ArrayList<Goods> result = new ArrayList<>();
        good.ifPresent(result::add);
        model.addAttribute("good", result);
        return "showGoods";
    }

    @GetMapping("/goods/{id}/edit")
    public String goodsEditPage(@PathVariable(value = "id") long id, Model model){
        if(!goodsRepository.existsById(id))
        {
            return "redirect:/";
        }
        Optional<Goods> good = goodsRepository.findById(id);
        ArrayList<Goods> result = new ArrayList<>();
        good.ifPresent(result::add);
        model.addAttribute("good", result);
        return "editGoods";
    }

    @PostMapping("/goods/{id}/edit")
    public String goodsEdit(@PathVariable(value = "id") long id, @RequestParam String name, @RequestParam String brand,
                           @RequestParam String image, @RequestParam Double price, Model model){

        Goods good = goodsRepository.findById(id).orElseThrow();
        good.setName(name);
        good.setBrand(brand);
        good.setImage(image);
        good.setPrice(price);
        goodsRepository.save(good);

        return "redirect:/goods/{id}";
    }
    @PostMapping("/goods/{id}/remove")
    public String goodsRemove(@PathVariable(value = "id") long id, Model model){

        Goods good = goodsRepository.findById(id).orElseThrow();
        goodsRepository.delete(good);

        return "redirect:/";
    }
}
