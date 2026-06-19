package com.Security.Authify.repository;

import com.Security.Authify.entity.TeacherEntity;
import com.Security.Authify.io.TeacherResponse;
import org.springframework.data.domain.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeacherRepository extends JpaRepository<TeacherEntity, Long>, JpaSpecificationExecutor<TeacherEntity> {

    Optional<TeacherEntity> findById(Long id);
// first page pe cursor=null, null is null = true, -> first page fetch karo.
    @Query("""
        SELECT t FROM TeacherEntity t
        WHERE (:cursor is null or t.id > :cursor) 
        ORDER BY t.id ASC
        """)
//    SELECT * FROM teacher WHERE id > 5 ORDER BY id ASC Limit 10
    List<TeacherEntity> fetchNextPage(@Param("cursor") Long cursor, Pageable pageable);
//    pageable internally apply limit, that is why we r returning list
}
