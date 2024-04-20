package com.example.demo.controllers;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * Осигурява началната страница на приложението.
     *
     * @return Името на началния шаблон на изгледа.
     */
    @GetMapping("/index")
    public String home(){
        return "index";
    }
    /**
     * Показва формата за регистрация на нови потребители.
     * Създава нов обект UserDto и го добавя към модела за използване във формата.
     *
     * @param model Моделът, който предава данни към изгледа.
     * @return Името на шаблона на формата за регистрация.
     */
    @GetMapping("/register")
    public String showRegistrationForm(Model model){
        UserDto user = new UserDto();
        model.addAttribute("user", user);
        return "register";
    }
    /**
     * Обработва данните от формата за регистрация на нов потребител.
     * Проверява дали потребителят вече съществува и ако не, записва новия потребител в системата.
     *
     * @param userDto Обектът, съдържащ данните на новия потребител.
     * @param model Моделът за предаване на данни към изгледа.
     * @return Пренасочва към формата за регистрация със съобщение за успех или грешка.
     */
    @PostMapping("/register/save")
    public String registration(@Valid @ModelAttribute("user") UserDto userDto,Model model){
        User existingUser = userService.findByEmail(userDto.getEmail());

        if(existingUser != null && existingUser.getEmail() != null && !existingUser.getEmail().isEmpty()) {
            return "redirect:/register?error";
        }

        userService.saveUser(userDto);
        return "redirect:/register?success";
    }
    /**
     * Осигурява страницата за вход в системата.
     *
     * @return Името на шаблона на страницата за вход.
     */
    @RequestMapping("/login")
    public String login(){
        return "login";
    }

    /**
     * Показва списък на всички потребители.
     * Извлича данните за всички потребители чрез UserService и ги добавя към модела.
     *
     * @param model Моделът за предаване на данни към изгледа.
     * @return Името на шаблона за показване на потребителите.
     */
    @GetMapping("/users")
    public String users(Model model){
        List<UserDto> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "users";
    }

    /**
     * Обработва заявка за изтриване на потребител по електронен адрес.
     *
     * @param email Електронният адрес на потребителя за изтриване.
     * @param model Моделът за предаване на данни към изгледа.
     * @return Пренасочва към списъка на потребителите със съобщение за изтриване.
     */
    @PostMapping("/users/delete")
    public String usersDelete(@Valid @ModelAttribute("email") String email, Model model){

        userService.deleteUser(email);
        return "redirect:/users?delete";

    }
    /**
     * Показва формата за редактиране на данните на потребител.
     *
     * @param email Електронният адрес на потребителя за редактиране.
     * @param model Моделът за предаване на данни към изгледа.
     * @return Шаблонът за редактиране на потребителя.
     */
    @PostMapping("/users/edit")
    public String usersEdit(@Valid @ModelAttribute("email") String email, Model model){
        //userService.editUser(user);
        User user=userService.findByEmail(email);
        model.addAttribute("user", user);
        return "edit";

    }
    /**
     * Съхранява промените, направени в данните на потребителя, след редакция.
     *
     * @param userDto Обновените данни на потребителя.
     * @param model Моделът за предаване на данни към изгледа.
     * @return Пренасочва към списъка на потребителите със съобщение за успех.
     */
    @PostMapping("/users/save")
    public String usersEditSave(@Valid @ModelAttribute("user") UserDto userDto,Model model){
        userService.editUser(userDto);
        return "redirect:/users";
    }
}
