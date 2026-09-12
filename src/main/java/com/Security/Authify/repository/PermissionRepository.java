package com.Security.Authify.repository;

import com.Security.Authify.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<PermissionRepository> findByName(String name);
}
