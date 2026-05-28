package com.Security.Authify.repository;

import com.Security.Authify.entity.StudentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {

    Optional<StudentEntity> findByStdId(String stdId);

    boolean existsByStdId(String stdId);

    boolean existsByUsername(String username);

}
