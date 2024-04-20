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

    /**
     * Обработва подаването на заявка за добавяне на продукт към поръчка. Използва продукта и текущия аутентикиран потребител,
     * за да създаде или актуализира поръчка.
     *
     * @param Id Идентификационният номер на продукта, който да се добави.
     * @param model Моделът, който предава атрибути към изгледа.
     * @param authentication Текущият обект за аутентикация, съдържащ данни за потребителя.
     * @return Пренасочва към началната страница след добавяне на продукта.
     */
    @PostMapping("/addProductInOrder")
    public String addProductInOrder(@Valid @ModelAttribute("productId") Long Id, Model model, Authentication authentication){
        Product product=productService.findById(Id);

        orderService.saveOrder(product, userService.findByEmail(authentication.getName()));
        return "redirect:/frontPage";
    }
    /**
     * Показва количката за пазаруване с поръчките, общото количество и общата цена, изчислени за текущия потребител.
     *
     * @param model Моделът за предаване на атрибути към изгледа.
     * @param authentication Текущият обект за аутентикация за идентифициране на потребителя.
     * @return Изгледът на количката за пазаруване, попълнен с детайли за поръчките, общото количество и общата цена.
     */
    @PostMapping("/shoppingCart")
    public String shoppingCart(Model model, Authentication authentication){
        List<OrderDto> orderDtos = orderService.getAllOrders(userService.findByEmail(authentication.getName()));
        int totalQuantity= orderService.totalQuantity(userService.findByEmail(authentication.getName()));
        Double totalPrice= orderService.totalPrice(userService.findByEmail(authentication.getName()));

        model.addAttribute("orders", orderDtos);
        model.addAttribute("totalQuantity", totalQuantity);
        model.addAttribute("totalPrice", totalPrice);
        return "shoppingCart";
    }
    /**
     * Добавя посочения продукт към количката за пазаруване на текущия потребител.
     *
     * @param product Продуктът, който да се добави в количката.
     * @param model Моделът за предаване на атрибути към изгледа.
     * @param authentication Текущият обект за аутентикация, съдържащ данни за потребителя.
     * @return Пренасочва към количката за пазаруване след добавяне на продукта.
     */
    @PostMapping("/addProductInCart")
    public String addProductInOrder(@Valid @ModelAttribute("product") Product product, Model model, Authentication authentication){

        orderService.saveOrder(product, userService.findByEmail(authentication.getName()));
        return "redirect:/shoppingCart";
    }
}
