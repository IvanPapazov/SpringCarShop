package com.example.demo.controllers;

import com.example.demo.dto.OrderDto;
import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.Order;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.OrderService;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private ProductService productService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderService orderService;


    @PostMapping("/addProductInOrder")
    public String addProductInOrder(@Valid @ModelAttribute("productId") Long Id, Model model, Authentication authentication){
        Product product=productService.findById(Id);

        orderService.saveOrder(product, userService.findByEmail(authentication.getName()));
        return "redirect:/frontPage";
    }

    @PostMapping("/shoppingCart")
    public String shoppingCart(Model model, Authentication authentication){
        List<OrderDto> orderDtos = orderService.getAllOrders(userService.findByEmail(authentication.getName()));
        model.addAttribute("orders", orderDtos);
        return "shoppingCart";
    }

}
