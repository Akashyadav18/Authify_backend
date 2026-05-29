package com.Security.Authify.securityConfig;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component("auditorAware")
public class AuditorAwareImpl implements AuditorAware<String> {
    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(auth -> auth.isAuthenticated() && !(auth.getPrincipal() instanceof String)) // skip "anonymousUser"
                .map(auth -> {
//                    User user = (User) auth.getPrincipal();// this will only return username, role
                    User user = (User) auth.getPrincipal(); // this return all values from userEntity class
                    return user.getUsername(); // or user.getUsername() or user.getId()
                });
    }
}
