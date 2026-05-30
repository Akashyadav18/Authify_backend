package com.Security.Authify.jwtUtils;

import com.Security.Authify.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

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

    public String getCurrentUserRole(){
        return getCurrentuser().getRole().name();
    }

    public boolean isAdmin(){
        return getCurrentuser().getRole().name().equals("ADMIN");
    }
}
