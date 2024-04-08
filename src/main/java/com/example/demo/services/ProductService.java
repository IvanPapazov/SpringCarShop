package com.example.demo.services;

import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Async
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;
    @Async
    void addMethod(String brand, String model, int year, String description, Double price, List<String> images)
    {
        Product product=new Product(brand,model,year,description,price);
        product.setImages(images);
        productsRepository.save(product);
    }
}
