package com.pluralsight.repository;

import com.pluralsight.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findByRole(String role);

    Optional<User> findByUsername(String username);
}
