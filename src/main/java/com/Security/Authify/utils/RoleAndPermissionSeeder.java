package com.Security.Authify.utils;

import com.Security.Authify.entity.ERole;
import com.Security.Authify.entity.Permission;
import com.Security.Authify.entity.Role;
import com.Security.Authify.repository.PermissionRepository;
import com.Security.Authify.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class RoleAndPermissionSeeder {
    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;
    public RoleAndPermissionSeeder(
            RoleRepository roleRepository,
            PermissionRepository permissionRepository
    ) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }
    @PostConstruct
    public void seedRolesAndPermissions() {
        // Avoid duplicate seeding
        if (roleRepository.findByName(ERole.ADMIN).isPresent()) {
            return;
        }
        Permission studentRead = permissionRepository.save( new Permission(null, "STUDENT_READ") );
        Permission studentCreate = permissionRepository.save( new Permission(null, "STUDENT_CREATE") );
        Permission studentUpdate = permissionRepository.save( new Permission(null, "STUDENT_UPDATE") );
        Permission studentDelete = permissionRepository.save( new Permission(null, "STUDENT_DELETE") );
        Permission teacherRead = permissionRepository.save( new Permission(null, "TEACHER_READ") );
        Permission teacherCreate = permissionRepository.save( new Permission(null, "TEACHER_CREATE") );
        Permission teacherUpdate = permissionRepository.save( new Permission(null, "TEACHER_UPDATE") );
        Permission teacherDelete = permissionRepository.save( new Permission(null, "TEACHER_DELETE") );

        // Create ADMIN role with all permissions
        Role admin = new Role();
        admin.setName(ERole.ADMIN);
        admin.setPermissions(
                Set.of( studentRead, studentCreate, studentUpdate, studentDelete, teacherRead, teacherCreate, teacherUpdate, teacherDelete
                ));
        roleRepository.save(admin);

        // Create TEACHER role with permissions
        Role teacher = new Role();
        teacher.setName(ERole.TEACHER);
        teacher.setPermissions
                (Set.of( studentRead, studentCreate, studentUpdate, studentDelete, teacherRead ));
        roleRepository.save(teacher);

        // Create STUDENT role with permissions
        Role student = new Role();
        student.setName(ERole.STUDENT);
        student.setPermissions(Set.of( studentRead ));
        roleRepository.save(student);
    }
}
