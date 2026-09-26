package com.Security.Authify.utils;

import com.Security.Authify.entity.AccountStatus;
import com.Security.Authify.entity.ERole;
import com.Security.Authify.entity.Role;
import com.Security.Authify.entity.UserEntity;
import com.Security.Authify.repository.RoleRepository;
import com.Security.Authify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        String email = "admin@gmail.com";

        Role adminRole = roleRepository.findByName(ERole.ADMIN)
                .orElseThrow(() -> new RuntimeException("Admin role not found"));

        if (userRepository.existsByEmail(email)) {
            log.info("Admin already exists. Skipping seeding.");
            return;
        }

        UserEntity user = new UserEntity();
        user.setName("Admin");
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setStatus(AccountStatus.APPROVED);
        user.setIsAccountVerified(true);
        user.setRoles(Set.of(adminRole));

        userRepository.save(user);
        log.info("Admin seeded successfully");
    }
}
