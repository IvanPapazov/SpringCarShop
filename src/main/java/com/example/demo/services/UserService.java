package com.example.demo.services;

import com.example.demo.Dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.User_Privilege;
import com.example.demo.entities.enums.User_Type;
import com.example.demo.repositories.UserRepository;
import lombok.Synchronized;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Synchronized
    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setFull_name(userDto.getFull_name());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setPhone_number(userDto.getPhone_number());
        user.setUser_role(User_Type.User);
        user.setPrivilege(User_Privilege.None);
        userRepository.save(user);
    }


    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

}
