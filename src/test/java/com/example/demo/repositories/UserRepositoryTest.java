package com.example.demo.repositories;

import com.example.demo.entities.Product;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.User_Privilege;
import com.example.demo.entities.enums.User_Type;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;
    @Test
    void saveMethod()
    {
        User user=new User();
        user.setFull_name("Ivan Papazov");
        user.setUsername("Az123");
        user.setPassword("123");
        user.setEmail("ivan_papazov@mail.bg");
        user.setPhone_number("0876088100");
        user.setAddress("San Stefano 10");
        user.setUser_role(User_Type.Admin);
        user.setPrivilege(User_Privilege.None);
        userRepository.save(user);
    }

    @Test
    void updateMethod()
    {
        User user=userRepository.findById(1L).get();
        user.setUser_role(User_Type.Admin);
        userRepository.save(user);
    }

    @Test
    void deleteByIdMethod()
    {
        userRepository.deleteById(1L);
    }

}