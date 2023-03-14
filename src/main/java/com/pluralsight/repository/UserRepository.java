package com.pluralsight.repository;

import org.springframework.data.repository.CrudRepository;

import com.pluralsight.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    // @Query("SELECT i FROM Application WHERE i.name = ?1")
    // Optional<User> findUserByName(String name);
}
