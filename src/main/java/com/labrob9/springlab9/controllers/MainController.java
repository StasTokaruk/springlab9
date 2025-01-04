package com.labrob9.springlab9.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class MainController {

    @GetMapping("/home")
    public String greeting(Model model) {
        model.addAttribute("title", "Головна");
        return "home";
    }

}