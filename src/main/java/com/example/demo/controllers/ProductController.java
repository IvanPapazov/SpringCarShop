package com.example.demo.controllers;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.services.ProductService;
import com.example.demo.services.UserService;
import jakarta.validation.Path;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Controller
public class ProductController {
    @Autowired
    private ProductService productService;

    public static String UPLOAD_DIRECTORY = ProductController.class.getResource("/static/images/").getFile();

    @GetMapping("/frontPage")
    public String frontPage(Model model){
        List<ProductDto> products = productService.getAllProducts();
        model.addAttribute("products", products);
        return "frontPage";
    }

    @GetMapping("/addProducts")
    public String showAddProductsForm(Model model){
        ProductDto product = new ProductDto();
        model.addAttribute("product", product);
        return "addProducts";
    }

    @PostMapping("/addProducts/save")
    public String addProduct(@Valid @ModelAttribute("product") ProductDto productDto, Model model,
                             @RequestPart("image") MultipartFile file) throws IOException {
        Product existingProduct = productService.findByName(productDto.getName());

        // Save the file to the server
        String fileName = file.getOriginalFilename();

        file.transferTo(new File(UPLOAD_DIRECTORY + fileName));// ToDo...

        productDto.setImages(fileName);

        if(existingProduct !=null) {
            return "redirect:/addProducts?error";
        }

        productService.addProduct(productDto);
        return "redirect:/addProducts?success";
    }

}
