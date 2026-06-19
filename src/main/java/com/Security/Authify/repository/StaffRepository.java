package com.Security.Authify.repository;

import com.Security.Authify.entity.Staff;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, Long> {
    @Query("""
            SELECT s FROM Staff s
            WHERE (:cursor is null or s.id > :cursor)
            ORDER BY s.id ASC
            """)
    List<Staff> fetchNextPage(@Param("cursor") Long cursor, Pageable pageable);
}
