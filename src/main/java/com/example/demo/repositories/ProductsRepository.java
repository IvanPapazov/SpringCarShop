package com.example.demo.repositories;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductsRepository extends JpaRepository<Product,Long> {
    /**
     * Търси и връща продукт по зададено име.
     * Методът извършва търсене в базата данни за продукт, чието име точно съвпада с предоставеното.
     * Връща продукта, ако е намерен. В противен случай връща {@code null}, което означава,
     * че продукт с такова име не съществува в системата.
     *
     * @param name Името на продукта, който се търси.
     * @return Намереният продукт, ако съществува такъв с това име; в противен случай {@code null}.
     */
    Product findByName(String name);
}
