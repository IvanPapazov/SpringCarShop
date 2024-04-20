package com.example.demo.repositories;

import com.example.demo.entities.Order;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
    /**
     * Търси и връща поръчка свързана с конкретен продукт.
     * Методът извършва търсене в базата данни за поръчка, която включва специфицирания продукт.
     * Връща първата намерена поръчка или {@code null}, ако няма поръчка свързана с този продукт.
     *
     * @param product Обектът продукт, за който се търси поръчка.
     * @return Обектът поръчка, който съдържа този продукт, или {@code null} ако такава поръчка не съществува.
     */
    Order findByProduct(Product product);
}
