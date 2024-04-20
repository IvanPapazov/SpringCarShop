package com.example.demo.repositories;

import com.example.demo.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    /**
     * Търси и връща потребител по подадения електронен адрес.
     * Методът извършва търсене в базата данни за потребител, чийто електронен адрес съответства на подадения.
     * Връща обекта потребител, ако съществува такъв със зададения имейл, или {@code null}, ако потребител с този имейл не е намерен.
     *
     * @param email Електронният адрес на потребителя, който се търси.
     * @return Обектът потребител, свързан със зададения електронен адрес, или {@code null}, ако такъв потребител не съществува.
     */
    User findByEmail(String email);

}
