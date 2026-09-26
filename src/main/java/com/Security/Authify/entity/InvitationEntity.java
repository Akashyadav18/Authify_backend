package com.Security.Authify.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "invitations")
public class InvitationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String email;
    @ManyToOne
    @JoinColumn(name = "invited_role_id", nullable = false)
    private Role invitedRole;
    @Column(length = 500, unique = true)
    private String token;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InvitationStatus status = InvitationStatus.PENDING;
    @ManyToOne
    @JoinColumn(name = "invited_by_admin_id", nullable = false)
    private UserEntity invitedByAdmin;
    @ManyToOne
    @JoinColumn(name = "accepted_user_id")
    private UserEntity acceptedUser;
    @Column(nullable = false)
    private LocalDateTime expiresAt;
    private LocalDateTime acceptedAt;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;
}
