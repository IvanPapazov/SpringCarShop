package com.example.demo.services;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.User_Privilege;
import com.example.demo.entities.enums.User_Type;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

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

    public List<UserDto> findAllUsers() {
        List<UserDto> dtoUsers=new ArrayList<UserDto>();
        List<User> users=userRepository.findAll();
        for (User user : users) {
            UserDto dto=new UserDto();
            dto.setId(user.getId());
            dto.setEmail(user.getEmail());
            dto.setAddress(user.getAddress());
            dto.setPassword(user.getPassword());
            dto.setUserRole(user.getUser_role());
            dto.setUsername(user.getUsername());
            dto.setPrivilege(user.getPrivilege());
            dto.setFull_name(user.getFull_name());
            dto.setPhone_number(user.getPhone_number());
            dtoUsers.add(dto);
        }
        return dtoUsers;
    }
    public Boolean isAccountExist(String email,String pass) {
        List<User> users=userRepository.findAll();
        for (User user : users) {
            if(user.getEmail().equals(email) && user.getPassword().equals(pass))
                return true;
        }
        return false;
    }

    public void deleteUser(String email)
    {
        userRepository.deleteById(findByEmail(email).getId());
    }

}
