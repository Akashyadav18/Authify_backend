package com.Security.Authify.repository;

import com.Security.Authify.entity.InvitationAuditLogEntity;
import com.Security.Authify.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InvitationAuditLogRepository extends JpaRepository<InvitationAuditLogEntity, Long> {

    List<InvitationAuditLogEntity> findByInvitation(InvitationEntity invitation);
}
