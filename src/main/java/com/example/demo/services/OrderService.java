package com.example.demo.services;

import com.example.demo.dto.OrderDto;
import com.example.demo.entities.Order;
import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.UserPrivilege;
import com.example.demo.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    /**
     * Търси поръчка по даден продукт.
     * Методът извиква repository за търсене на поръчка, която съдържа специфичния продукт.
     *
     * @param product Продуктът, за който търсим поръчка.
     * @return Връща поръчката, ако е намерена такава, или {@code null} ако поръчка с този продукт не съществува.
     */
    public Order findByProduct(Product product) {
        return orderRepository.findByProduct(product);
    }
    /**
     * Запазва или обновява поръчка за даден продукт и потребител. Ако поръчката съществува, увеличава количеството на продукта.
     * Прилага отстъпки в зависимост от нивото на привилегии на потребителя.
     *
     * @param product Продуктът, който се добавя към поръчката.
     * @param user Потребителят, правещ поръчката.
     */
    public void saveOrder(Product product, User user) {
        Order order = findByProduct(product); // Проверка за вече съществуваща поръчка

        if (order == null) {
            // Ако поръчката не съществува, създайте нова поръчка
            order = new Order();
            order.setQuantity(1);
            order.setProduct(product);
            order.setUser(user);
        } else {
            // Ако поръчката вече съществува, увеличете количеството
            order.setQuantity(order.getQuantity() + 1);
        }

        order.setFinalPrice(calculateFinalPrice(order));
        orderRepository.save(order);
    }

    /**
     * Връща крайната цена на поръчката.
     * @param order Продуктът, на който се смята цената.
     * @return Цената на продуцта
     */
    public double calculateFinalPrice(Order order)
    {
        double price = order.getQuantity() * order.getProduct().getPrice();
        if (order.getUser().getPrivilege()== UserPrivilege.Diamond) {
            price *= 0.5;
        } else if (order.getUser().getPrivilege()== UserPrivilege.Gold) {
            price *= 0.6;
        } else if (order.getUser().getPrivilege()== UserPrivilege.Silver) {
            price *= 0.7;
        } else if (order.getUser().getPrivilege()== UserPrivilege.Bronze) {
            price *= 0.8;
        } else if (order.getUser().getPrivilege() == UserPrivilege.Iron) {
            price *= 0.9;
        }

        return price;
    }
    /**
     * Изтрива поръчката, свързана с посочения продукт, от базата данни.
     * Този метод първо извлича поръчката, която е свързана с дадения продукт. Ако съществува такава поръчка,
     * тя се премахва от базата данни. Ако не бъде открита поръчка, методът приключва без да модифицира базата данни.
     *
     * @param product Продуктът, свързан с поръчката, която трябва да бъде изтрита. Методът предполага, че всеки продукт
     *                може да бъде свързан само с една поръчка. Продуктът не трябва да е null и трябва да съдържа
     *                валидна идентификационна информация, както е необходимо по метода {@code findByProduct}.
     */
    public void delateOrder(Product product)
    {
        Order order=findByProduct(product);
        if(order!=null) {
            orderRepository.delete(order);
        }
    }
    /**
     * Връща всички поръчки за даден потребител.
     *
     * @param user Потребителят, за когото се търсят поръчки.
     * @return Списък от DTO обекти, съдържащи информация за поръчките на потребителя.
     */
    public List<OrderDto> getAllOrders(User user) {
        List<OrderDto> dtoOrder = new ArrayList<>();
        List<Order> orders = orderRepository.findAll().stream().filter(order -> order.getUser() == user).toList();
        for (Order order : orders) {
            OrderDto dto = new OrderDto();
            dto.setId(order.getId());
            dto.setProductImage(order.getProduct().getImages());
            dto.setProductName(order.getProduct().getName());
            dto.setProductDescription(order.getProduct().getDescription());
            dto.setQuantity(order.getQuantity());
            dto.setUser(user);
            dto.setProduct(order.getProduct());
            dto.setFinalPrice(calculateFinalPrice(order));
            dtoOrder.add(dto);
        }
        return dtoOrder;
    }
    /**
     * Изчислява общото количество продукти във всички поръчки на даден потребител.
     *
     * @param user Потребителят, за когото се изчислява общото количество.
     * @return Общото количество на продуктите.
     */
    public int totalQuantity(User user) {
        List<OrderDto> orderDtos = this.getAllOrders(user);
        int totalQuantity = 0;
        for (OrderDto product : orderDtos) {
            totalQuantity = totalQuantity + product.getQuantity();
        }
        return  totalQuantity;
    }
    /**
     * Изчислява общата цена на всички поръчки на даден потребител.
     *
     * @param user Потребителят, за когото се изчислява общата цена.
     * @return Общата цена на поръчките.
     */
    public Double totalPrice(User user) {
        List<OrderDto> orderDtos = this.getAllOrders(user);
        double totalPrice = 0.0;
        for (OrderDto product : orderDtos) {
            totalPrice = totalPrice + product.getFinalPrice();
        }
        return  totalPrice;
    }

}
