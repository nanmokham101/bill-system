package com.test.student.repository;

import com.test.student.models.Student;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    boolean existsByEmail(String email);
    Student findByEmail(String email);

    boolean existsByPhoneNumber(String brand);

    List<Student> findAll(Specification<Student> specification);
}
