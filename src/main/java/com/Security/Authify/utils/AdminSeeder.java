package com.Security.Authify.utils;

import com.Security.Authify.entity.AccountStatus;
import com.Security.Authify.entity.UserEntity;
import com.Security.Authify.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class AdminSeeder implements ApplicationRunner {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        UserEntity user = new UserEntity();
        user.setName("Admin");
        user.setEmail("admin@gmail.com");
        user.setPassword(passwordEncoder.encode("12345678"));
        user.setStatus(AccountStatus.APPROVED);
        user.setIsAccountVerified(true);

        userRepository.save(user);
        log.info("Admin seeded successfully");
    }
}
