package com.example.demo.repositories;

import com.example.demo.entities.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class ProductRepositoryTest {
    @Autowired
    private ProductsRepository productsRepository;

    @Test
    void saveMethod()
    {
        Product product=new Product();
        productsRepository.save(product);
    }


    @Test
    void updateMethod()
    {
        Product product=productsRepository.findById(1L).get();
        product.setPrice(190000.00);
        productsRepository.save(product);
    }

    @Test
    void deleteByIdMethod()
    {
        productsRepository.deleteById(1L);
    }
}
