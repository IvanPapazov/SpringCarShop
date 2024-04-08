package com.example.demo.entities;

import com.example.demo.entities.enums.Order_Status;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    private long Id;
    // To Do...........
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customer_id", referencedColumnName = "Id")
    private User customer_id;
    // To Do...........
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "product_id", referencedColumnName = "Id")
    private Product product_id;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Order_Status status;
    @Column(nullable = false)
    private Double final_price;
    @Column(nullable = false)
    private LocalDate create_date;

    public long getId() {
        return Id;
    }

    public void setId(long id) {
        Id = id;
    }

    public User getCustomer_id() {
        return customer_id;
    }

    public void setCustomer_id(User customer_id) {
        this.customer_id = customer_id;
    }

    public Product getProduct_id() {
        return product_id;
    }

    public void setProduct_id(Product product_id) {
        this.product_id = product_id;
    }

    public Order_Status getStatus() {
        return status;
    }

    public void setStatus(Order_Status status) {
        this.status = status;
    }

    public Double getFinal_price() {
        return final_price;
    }

    public void setFinal_price(Double final_price) {
        this.final_price = final_price;
    }

    public LocalDate getCreate_date() {
        return create_date;
    }

    public void setCreate_date(LocalDate create_date) {
        this.create_date = create_date;
    }
}
