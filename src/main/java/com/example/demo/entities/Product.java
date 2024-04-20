package com.example.demo.entities;

import com.example.demo.entities.enums.ProductGamingPlatform;
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
    private ProductGamingPlatform gamingPlatform;
    @Column(nullable = false)
    private int quantity;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private Double price;
    private String images;
    @OneToMany(mappedBy = "product",cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Order> order;

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

    public ProductGamingPlatform getGamingPlatform() {
        return gamingPlatform;
    }

    public void setGamingPlatform(ProductGamingPlatform gamingPlatform) {
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

    public List<Order> getProducts() {
        return order;
    }

    public void setProducts(List<Order> order) {
        this.order = order;
    }
}
