package com.Security.Authify.jwtUtils;

import com.Security.Authify.entity.ERole;
import com.Security.Authify.entity.Role;
import com.Security.Authify.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class AuthUtil {

    public UserEntity getCurrentuser(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) auth.getPrincipal();
    }

    public String getCurrentUserEmail(){
        return getCurrentuser().getEmail();
    }

    public String getCurrentUserId(){
        return getCurrentuser().getUserId();
    }

    public Set<Role> getCurrentUserRole(){
        return getCurrentuser().getRoles();
    }

    public boolean isAdmin(){
        return getCurrentuser().getRoles()
                .stream()
                .anyMatch(role -> role.getName() == ERole.ADMIN);

    }
}
