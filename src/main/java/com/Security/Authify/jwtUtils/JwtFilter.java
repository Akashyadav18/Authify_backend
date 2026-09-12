package com.Security.Authify.jwtUtils;

import com.Security.Authify.entity.Role;
import com.Security.Authify.service.AppUserDetailService;
import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class JwtFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final AppUserDetailService appUserDetailService;

    private static final List<String> PUBLIC_URL= List.of("/api/auth/register", "/api/auth/login", "/api/auth/send-reset-otp", "/api/auth/reset-password");

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getServletPath();
        if(PUBLIC_URL.contains(path)){
            filterChain.doFilter(request, response);
            return;
        }
        String token = null;
        String email = null;

        //1. Check authorization header
        String headerToken = request.getHeader("Authorization");
        if(headerToken != null && headerToken.startsWith("Bearer ")){
            token = headerToken.substring(7);
        }
        //2. if token not found in header
        if(token == null){
            Cookie[] cookies = request.getCookies();
            if(cookies != null){
                for(Cookie cookie : cookies){
                    if("jwt".equals(cookie.getName())){
                        token = cookie.getValue();
                        break;
                    }
                }
            }
        }

        if (token != null) {
            email = jwtUtil.extractEmail(token);
            if(email != null && SecurityContextHolder.getContext().getAuthentication() == null){
                //1. loadUserbyusername- auth credentials ko verify karne k baad userEntity object load/return karta hai
                UserDetails userDetails = appUserDetailService.loadUserByUsername(email);
                if(jwtUtil.validateToken(token, userDetails)){
                    //6. Authentication Object create karta hai
                    UsernamePasswordAuthenticationToken authenticationToken =
                            new UsernamePasswordAuthenticationToken(
                                    userDetails, // ← YAHAN store hota hai — yahi getPrincipal() deta hai
                                    null,           // Password(credentials) null hai
                                    userDetails.getAuthorities()); // Permissions add karta hai
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    //7. AuthenticationToken ko SecurityContext mein set karta hai
                    SecurityContextHolder.getContext().setAuthentication(authenticationToken); // Ab poori app mein kahi se bhi nikal sakte ho
                }
            }
        }

        filterChain.doFilter(request, response);
    }
}
