package com.example.demo.repositories;

import com.example.demo.entities.User;
import com.example.demo.entities.enums.UserPrivilege;
import com.example.demo.entities.enums.UserType;
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
        user.setName("Ivan Papazov");
        user.setUsername("Az123");
        user.setPassword("123");
        user.setEmail("ivan_papazov@mail.bg");
        user.setPhoneNumber("0876088100");
        user.setAddress("San Stefano 10");
        user.setUserRole(UserType.Admin);
        user.setPrivilege(UserPrivilege.None);
        userRepository.save(user);
    }

    @Test
    void updateMethod()
    {
        User user=userRepository.findById(1L).get();
        user.setUserRole(UserType.Admin);
        userRepository.save(user);
    }

    @Test
    void deleteByIdMethod()
    {
        userRepository.deleteById(1L);
    }

}