package com.Security.Authify.repository;

import com.Security.Authify.entity.InvitationEntity;
import com.Security.Authify.entity.InvitationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<InvitationEntity, Long> {

    List<InvitationEntity> findByStatus(InvitationStatus status);
    Optional<InvitationEntity> findByEmail(String email);
    Optional<InvitationEntity> findByToken(String token);

    // Pending and not expired invitations
    @Query(" Select i from InvitationEntity i "
            + " WHERE i.status='Pending' " +
            " AND i.expiresAt > CURRENT_TIMESTAMP ")
    List<InvitationEntity> findActivePendingInvitations();

    //Find expired and pending invitations
    @Query(" SELECT i FROM InvitationEntity i "
            + " WHERE i.status='PENDING' " +
            " AND i.expiresAt <= CURRENT_TIMESTAMP ")
    List<InvitationEntity> findExpiresPendingInvitations();
}
