package com.example.demo.dto;


import com.example.demo.entities.enums.User_Privilege;
import com.example.demo.entities.enums.User_Type;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto
{
    private Long id;
    @NotEmpty
    private String full_name;
    @NotEmpty
    private String username;
    @NotEmpty(message = "Password should not be empty")
    private String password;
    @NotEmpty(message = "Email should not be empty")
    @Email
    private String email;
    @NotEmpty
    private String phone_number;
    @NotEmpty
    private String address;
    private User_Privilege privilege;
    private User_Type userRole;

    public User_Privilege getPrivilege() {
        return privilege;
    }

    public void setPrivilege(User_Privilege privilege) {
        this.privilege = privilege;
    }

    public User_Type getUserRole() {
        return userRole;
    }

    public void setUserRole(User_Type userRole) {
        this.userRole = userRole;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
