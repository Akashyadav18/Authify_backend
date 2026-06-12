package com.Security.Authify.repository;

import com.Security.Authify.entity.TeacherEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long>, JpaSpecificationExecutor<TeacherEntity> {

    Optional<TeacherEntity> findById(Long id);
}
