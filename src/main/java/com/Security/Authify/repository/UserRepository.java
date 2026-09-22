package com.Security.Authify.repository;

import com.Security.Authify.entity.AccountStatus;
import com.Security.Authify.entity.UserEntity;
import com.Security.Authify.io.ApprovalResponse;
import com.Security.Authify.io.ProfileResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    List<UserEntity> findByStatus(AccountStatus status);
}
