package com.example.J2EE.repository;

import com.example.J2EE.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorRepository extends MongoRepository<Doctor, String> {
    Page<Doctor> findAll(Pageable pageable);
    List<Doctor> findByNameContainingIgnoreCase(String name);
}
