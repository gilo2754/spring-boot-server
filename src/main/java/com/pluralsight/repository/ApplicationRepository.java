package com.pluralsight.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.pluralsight.entity.Application;

public interface ApplicationRepository extends CrudRepository<Application, Long> {

    // @Query("SELECT i FROM Application WHERE i.name = ?1")
    Optional<Application> findApplicationByName(String name);
}
