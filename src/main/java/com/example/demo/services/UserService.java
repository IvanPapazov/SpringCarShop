package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.UserPrivilege;
import com.example.demo.entities.enums.UserType;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setUsername(userDto.getUsername());
        BCryptPasswordEncoder crypt = new BCryptPasswordEncoder();
        user.setPassword(crypt.encode(userDto.getPassword()));
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserRole(UserType.User);
        user.setPrivilege(UserPrivilege.None);
        userRepository.save(user);
    }

    public void editUser(UserDto userDto ) {
        User user = userRepository.findByEmail(userDto.getEmail());
        user.setName(userDto.getFullName());
        user.setEmail(userDto.getEmail());
        user.setAddress(userDto.getAddress());
        user.setUsername(userDto.getUsername());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setUserRole(userDto.getUserRole());
        user.setPrivilege(userDto.getPrivilege());
        userRepository.save(user);
    }



    public List<UserDto> findAllUsers() {
        List<UserDto> dtoUsers=new ArrayList<UserDto>();
        List<User> users=userRepository.findAll();
        for (User user : users) {
            UserDto dto=new UserDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setAddress(user.getAddress());
            dto.setPassword(user.getPassword());
            dto.setUserRole(user.getUserRole());
            dto.setUsername(user.getUsername());
            dto.setPrivilege(user.getPrivilege());
            dto.setFullName(user.getFullName());
            dto.setPhoneNumber(user.getPhoneNumber());
            dtoUsers.add(dto);
        }
        return dtoUsers;
    }

    public void deleteUser(String email)
    {
        userRepository.deleteById(findByEmail(email).getId());
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



}
