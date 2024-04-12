package com.example.demo.services;

import com.example.demo.dto.ProductDto;
import com.example.demo.dto.UserDto;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.User_Privilege;
import com.example.demo.entities.enums.User_Type;
import com.example.demo.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Async
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;

    public Product findByName(String name) {
        return productsRepository.findByName(name);
    }

    public void addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice((double)productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImages(productDto.getImages());
        product.setQuantity(productDto.getQuantity());
        product.setGamingPlatform(productDto.getGamingPlatform());
        productsRepository.save(product);
    }

    public List<ProductDto> getAllProducts() {
        List<ProductDto> dtoProduct=new ArrayList<ProductDto>();
        List<Product> products=productsRepository.findAll();
        for (Product product : products) {
            ProductDto dto=new ProductDto();
            dto.setImages(product.getImages());
            dto.setDescription(product.getDescription());
            dto.setId(product.getId());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setGamingPlatform(product.getGamingPlatform());
            dto.setQuantity(product.getQuantity());
            dtoProduct.add(dto);
        }
        return dtoProduct;
    }
}
