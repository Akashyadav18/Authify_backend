package com.Security.Authify.entity;

public enum InvitationStatus {
    PENDING,      // Awaiting user acceptance
    ACCEPTED,     // User has completed setup
    EXPIRED,      // Token has expired
    REVOKED       // Admin ne revoke kiya
}
