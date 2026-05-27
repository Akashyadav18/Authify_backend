package com.Security.Authify.entity;

import java.util.Set;

public enum Role {
    ROLE_ADMIN(Set.of(
            Permission.STUDENT_READ, Permission.STUDENT_CREATE, Permission.STUDENT_UPDATE, Permission.STUDENT_DELETE,
            Permission.TEACHER_READ, Permission.TEACHER_CREATE, Permission.TEACHER_UPDATE, Permission.TEACHER_DELETE
    )),
    ROLE_TEACHER(Set.of(
            Permission.STUDENT_READ,Permission.STUDENT_CREATE,Permission.STUDENT_UPDATE,Permission.STUDENT_DELETE,
            Permission.TEACHER_READ, Permission.TEACHER_UPDATE
    )),
    ROLE_USER(Set.of(
            Permission.STUDENT_READ
    ));


    private final Set<Permission> permissions;

    Role(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    public Set<Permission> getPermissions() {
        return permissions;
    }
}
