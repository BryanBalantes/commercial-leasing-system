package com.balantes.property_management_system.controller;

import com.balantes.property_management_system.config.USER_TYPE;
import com.balantes.property_management_system.dto.UserDTO;
import com.balantes.property_management_system.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public String getRegistrationPage(Model model) {
        model.addAttribute("title", "SignUp");
        model.addAttribute("user", new UserDTO());
        return "registration/registration";
    }

    @PostMapping
    public String register(@ModelAttribute("user") UserDTO user) {

        System.out.println("REGISTER CONTROLLER CALLED");
        user.setType(USER_TYPE.GENERAL.getType());
        userService.create(user);
        return "redirect:/";
    }
}
