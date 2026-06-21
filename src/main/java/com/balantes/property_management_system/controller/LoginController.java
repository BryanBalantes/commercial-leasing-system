package com.balantes.property_management_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class LoginController {

    @GetMapping
    public String index(Model model) {
        model.addAttribute("title", "D AND J RENTALS");
        return "login";
    }

}
