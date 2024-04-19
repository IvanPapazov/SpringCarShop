package com.example.demo.dto;

import com.example.demo.entities.enums.ProductGamingPlatform;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private long Id;
    @NotEmpty
    private String name;

    private ProductGamingPlatform gamingPlatform;

    private int quantity;
    @NotEmpty
    private String description;

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
}
