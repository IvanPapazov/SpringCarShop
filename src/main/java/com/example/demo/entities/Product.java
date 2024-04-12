package com.example.demo.entities;

import com.example.demo.entities.enums.Product_GamingPlatform;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long Id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private Product_GamingPlatform gamingPlatform;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    private String images;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Product_GamingPlatform getGamingPlatform() {
        return gamingPlatform;
    }

    public void setGamingPlatform(Product_GamingPlatform gamingPlatform) {
        this.gamingPlatform = gamingPlatform;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImages() {
        return images;
    }

    public void setImages(String images) {
        this.images = images;
    }
}
