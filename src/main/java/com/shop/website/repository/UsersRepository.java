package com.shop.website.repository;

import com.shop.website.models.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface UsersRepository extends JpaRepository<Users, Long> {
    Users findByUsername(String username);

    @Modifying
    @Query(value="select * from users", nativeQuery=true)
    Iterable<Users> getUsers(String username);

}
