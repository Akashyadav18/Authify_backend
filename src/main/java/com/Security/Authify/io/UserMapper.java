package com.Security.Authify.io;

import com.Security.Authify.custom.CustomGenerator;
import com.Security.Authify.entity.Role;
import com.Security.Authify.entity.UserEntity;
import com.Security.Authify.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final CustomGenerator customGenerator;
    private final RoleRepository roleRepo;

    public UserEntity convertToUserEntity(ProfileRequest request) {
//        Role role = roleRepo.findByName(request.getRole())
//                .orElseThrow(() -> new RuntimeException("Role not found"));
        return UserEntity.builder()
                .email(request.getEmail())
                .userId(customGenerator.generateUniqueId())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .isAccountVerified(false)
                .resetOtpExpiryAt(0L)
                .verifyOtp(null)
                .verifyOtpExpiryAt(0L)
                .resetOtp(null)
//                .roles(Set.of(role))
                .build();
    }

    public ProfileResponse convertToProfileResponse(UserEntity newProfile) {
        return ProfileResponse.builder()
                .name(newProfile.getName())
                .email(newProfile.getEmail())
                .userId(newProfile.getUserId())
                .isAccountVerified(newProfile.getIsAccountVerified())
                .build();
    }
}
