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
    @Autowired
    private OrderService orderService;


    /**
     * Намира продукт по име.
     *
     * @param name Името на продукта, който се търси.
     * @return Върнатият продукт, ако съществува с това име, или {@code null}, ако не е намерен.
     */
    public Product findByName(String name) {
        return productsRepository.findByName(name);
    }
    /**
     * Намира продукт по идентификационен номер.
     *
     * @param Id Идентификационният номер на продукта.
     * @return Продуктът, ако е намерен; в противен случай хвърля NoSuchElementException, ако продуктът не съществува.
     */
    public Product findById(Long Id) {
        return productsRepository.findById(Id).get();
    }
    /**
     * Добавя нов продукт в базата данни.
     *
     * @param productDto DTO обектът, съдържащ информация за продукта, който трябва да бъде добавен.
     */
    public void addProduct(ProductDto productDto) {
        Product product = new Product();
        product.setName(productDto.getName());
        product.setPrice(productDto.getPrice());
        product.setDescription(productDto.getDescription());
        product.setImages(productDto.getImages());
        product.setQuantity(productDto.getQuantity());
        product.setGamingPlatform(productDto.getGamingPlatform());
        productsRepository.save(product);
    }
    /**
     * Връща списък от всички продукти, представени като DTO.
     *
     * @return Списък на продуктови DTOs за всички налични продукти.
     */
    public List<ProductDto> getAllProducts() {
        List<ProductDto> dtoProduct=new ArrayList<>();
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
    /**
     * Извлича всички продукти от базата данни и ги преобразува в DTO формат.
     * Този метод е полезен за представяне на продуктите в потребителския интерфейс,
     * като осигурява всичка необходима информация за всеки продукт.
     *
     * @return Списък от ProductDto, който съдържа детайлна информация за всички налични продукти.
     */
    public List<ProductDto> findAllProducts() {
        List<ProductDto> productsDto=new ArrayList<>();
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
    /**
     * Обновява наличните количества на даден продукт в базата данни.
     * Използва се за увеличаване на количеството на склад за продуктите.
     *
     * @param product Продуктът, който трябва да бъде обновен.
     */
    public void refillProduct(Product product ) {
        productsRepository.save(product);
    }
    /**
     * Изтрива продукт от базата данни и свързания с него файл на изображението.
     * Това гарантира, че всички следи от продукта се премахват както от базата данни, така и от файловата система.
     *
     * @param Id Идентификационният номер на продукта, който трябва да бъде изтрит.
     * @param filePath Пътят към директорията, където се намира файлът с изображението на продукта.
     * @throws RuntimeException Ако изтриването на файл с изображението не успее.
     */
    public void deleteProduct(Long Id,String filePath)
    {
        Product product=findById(Id);
        orderService.delateOrder(product);
        Path path = Paths.get(filePath,product.getImages());
        try {
            Files.delete(path);
            productsRepository.deleteById(Id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
