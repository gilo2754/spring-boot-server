package com.pluralsight.repository;

import com.pluralsight.DTO.ClinicDTO;
import com.pluralsight.entity.Clinic;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface ClinicRepository extends CrudRepository<Clinic, Long> {

    List<Clinic> findBySpeciality(String speciality);

    @Query("SELECT c FROM Clinic c JOIN c.doctors u WHERE u.user_id = :user_id")
    List<Clinic> findClinicsByUserId(@Param("user_id") Long userId);
}
