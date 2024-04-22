package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.UserPrivilege;
import com.example.demo.entities.enums.UserType;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;
    /**
     * Създава и запазва нов потребител в базата данни на основата на предоставените данни от DTO.
     * Паролата на потребителя се криптира преди запазване за повишена сигурност.
     *
     * @param userDto DTO обект, съдържащ информацията за новия потребител.
     */
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
    /**
     * Редактира съществуващ потребител в базата данни, обновявайки неговата информация с тази от предоставения DTO.
     *
     * @param userDto DTO обект с обновените данни за потребителя.
     */
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


    /**
     * Извлича всички потребители от базата данни и ги връща като списък от DTOs.
     *
     * @return Списък от UserDto, представляващи всички потребители в системата.
     */
    public List<UserDto> findAllUsers() {
        List<UserDto> dtoUsers=new ArrayList<>();
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
    /**
     * Изтрива потребител от базата данни на основата на предоставения електронен адрес.
     *
     * @param email Електронният адрес на потребителя, който трябва да бъде изтрит.
     */
    public void deleteUser(String email)
    {
        userRepository.deleteById(findByEmail(email).getId());
    }
    /**
     * Намира потребител в базата данни по зададен електронен адрес.
     *
     * @param email Електронният адрес на потребителя за търсене.
     * @return Върнатият потребител, ако съществува; в противен случай {@code null}.
     */
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }



}
