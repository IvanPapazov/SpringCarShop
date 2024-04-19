package com.example.demo.services;

import com.example.demo.dto.ProductDto;
import com.example.demo.entities.Product;
import com.example.demo.repositories.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {
    @Autowired
    private ProductsRepository productsRepository;

    public Product findByName(String name) {
        return productsRepository.findByName(name);
    }
    public Product findById(Long Id) {
        return productsRepository.findById(Id.longValue()).get();
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

    public List<ProductDto> findAllProducts() {
        List<ProductDto> productsDto=new ArrayList<ProductDto>();
        List<Product> products=productsRepository.findAll();
        for (Product product : products) {
            ProductDto dto=new ProductDto();
            dto.setId(product.getId());
            dto.setQuantity(product.getQuantity());
            dto.setName(product.getName());
            dto.setPrice(product.getPrice());
            dto.setDescription(product.getDescription());
            dto.setImages(product.getImages());
            dto.setGamingPlatform(product.getGamingPlatform());
            productsDto.add(dto);
        }
        return productsDto;
    }

    public void refillProduct(Product product ) {
        productsRepository.save(product);
    }

    public void deleteProduct(Long Id,String filePath)
    {
        Product product=findById(Id);
        Path path = Paths.get(filePath,product.getImages());
        try {
            Files.delete(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        productsRepository.deleteById(Id.longValue());
    }
}
