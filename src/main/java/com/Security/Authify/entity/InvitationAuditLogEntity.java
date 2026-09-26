package com.Security.Authify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "invitation_audit_logs")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InvitationAuditLogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @ManyToOne
    @JoinColumn(name = "invitation_id", nullable = false)
    private InvitationEntity invitation;

    @Column(nullable = false)
    private String action;  // CREATED, RESENT, ACCEPTED, etc.

    @ManyToOne
    @JoinColumn(name = "actor_id")
    private UserEntity actor;

    @CreationTimestamp
    @Column(name = "timestamp", nullable = false, updatable = false)
    private LocalDateTime timestamp;

    @Column(length = 500)
    private String details;
}
