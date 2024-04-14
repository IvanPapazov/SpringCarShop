package com.example.demo.repositories;

import com.example.demo.dto.UserDto;
import com.example.demo.entities.User;
import com.example.demo.entities.enums.User_Privilege;
import com.example.demo.entities.enums.User_Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    User findByEmail(String email);

    /*@Modifying
    @Query(value = "UPDATE User u SET u.deliveryStatus = :username," +
            " u.password = :password, u.email = :email " +
            " u.phone_number = :phone_number, u.address = :address " +
            " u.privilege = :privilege, u.userRole = :userRole " +
            " u.full_name = :full_name WHERE u.id = :userId")
    void updateUser(@Param("username") String username,
                    @Param("password") String password,
                    @Param("email") String email,
                    @Param("phone_number") String phone_number,
                    @Param("address") String address,
                    @Param("privilege") User_Privilege privilege,
                    @Param("userRole") User_Type userRole,
                    @Param("userRole") String full_name,
                    @Param("userId") Long userId);*/

}
