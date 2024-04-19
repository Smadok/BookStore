package com.shop.web.controllers;

import com.shop.web.dto.RegistrationDto;
import com.shop.web.models.UserEntity;
import com.shop.web.services.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }
    @GetMapping("/login")
    public String loginForm()
    {
        return "login";
    }

    @GetMapping("/register")
    public String registerForm(Model model)
    {
        RegistrationDto user = new RegistrationDto();
        model.addAttribute("user",user);
        return "register";
    }
    @PostMapping("/register/save")
    public String register(@Valid @ModelAttribute("user")RegistrationDto user,
                           BindingResult result,
                           Model model)
    {
        UserEntity existingUserEmail= userService.findByEmail(user.getEmail());
        if (existingUserEmail !=null && existingUserEmail.getEmail()!=null && !existingUserEmail.getEmail().isEmpty())
        {
            result.rejectValue("email","Email/Username already taken");
        }
        UserEntity existingUsername= userService.findByUsername(user.getUsername());
        if (existingUsername !=null && existingUsername.getUsername()!=null && !existingUsername.getUsername().isEmpty())
        {
            result.rejectValue("username","Email/Username already taken");
        }
        if (result.hasErrors())
        {
            model.addAttribute("user",user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/bookShop?success";
    }
}
