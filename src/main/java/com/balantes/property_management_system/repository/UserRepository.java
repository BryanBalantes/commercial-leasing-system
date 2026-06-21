package com.balantes.property_management_system.repository;


import com.balantes.property_management_system.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmailAddress(String emailAddress);

    boolean existsByEmailAddressEquals(String emailAddress);
}
