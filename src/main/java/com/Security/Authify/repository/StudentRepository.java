package com.Security.Authify.repository;

import com.Security.Authify.entity.StudentEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long>, JpaSpecificationExecutor<StudentEntity> {

    Optional<StudentEntity> findByStdId(String stdId);

    @Query("""
            SELECT t FROM StudentEntity t
            WHERE (:cursor is null or t.id > :cursor) 
            ORDER BY t.id ASC
            """)
    List<StudentEntity> fetchNextPage(@Param("cursor") Long cursor, Pageable pageable);

}
