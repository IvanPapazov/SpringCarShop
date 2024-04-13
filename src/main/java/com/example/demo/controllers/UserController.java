package com.example.demo.controllers;

import com.example.demo.dto.LogInDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;


    @GetMapping("/index")
    public String home(){
        return "index";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,Model model){
        User existingUser = userService.findByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            return "redirect:/register?error";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @PostMapping("/login/user")
    public String loginUser(@Valid @ModelAttribute("user") LogInDto userDto, Model model){

        if(userDto==null||!userService.isAccountExist(userDto.getEmail(),userDto.getPassword())){
            return "redirect:/login?error";
        }
        return "redirect:/frontPage";
    }

    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }


    @PostMapping("/users/delete")
    public String usersDelete(@Valid @ModelAttribute("email") String email, Model model){

        userService.deleteUser(email);
        return "redirect:/users?delete";

    }

}
