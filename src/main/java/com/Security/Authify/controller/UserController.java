package com.Security.Authify.controller;

import com.Security.Authify.io.*;
import com.Security.Authify.jwtUtils.JwtUtil;
import com.Security.Authify.service.AppUserDetailService;
import com.Security.Authify.service.EmailService;
import com.Security.Authify.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final AppUserDetailService appUserDetailService;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
   public ProfileResponse register(@Valid @RequestBody ProfileRequest req){
       ProfileResponse res = userService.createProfile(req);
       emailService.sendWelcomeEmail(res.getEmail(), res.getName());
       return res;
   }

   @PostMapping("/login")
   public ResponseEntity<?> login(@RequestBody AuthRequest authRequest){
        try{
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            authRequest.getEmail(),
                            authRequest.getPassword()
                    )
            );
            UserDetails userDetails = appUserDetailService.loadUserByUsername(authRequest.getEmail());
            if(authenticate.isAuthenticated()) {
                String role = authenticate
                        .getAuthorities()
                        .iterator()
                        .next()
                        .getAuthority()
                        //we should always use prefix ROLE_ . This should be use when we want to inform spring security about the role. But don't add when passing in JWT
                        .replace("ROLE_", "");
                String jwtToken = jwtUtil.generateToken(userDetails, role);
                ResponseCookie cookie = ResponseCookie.from("jwt", jwtToken)
                        .httpOnly(true)
                        .path("/")
                        .maxAge(Duration.ofDays(1))
                        .sameSite("Strict")
                        .build();

                return ResponseEntity.ok()
                        .header(HttpHeaders.SET_COOKIE, cookie.toString())
                        .body(new AuthResponse(authRequest.getEmail(), jwtToken));
            }
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");

        }catch (BadCredentialsException ex){
            Map<String, Object> err = new HashMap<>();
            err.put("error", true);
            err.put("message", "Email or Password is incorrect");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(err);
        }catch (DisabledException ex){
            Map<String, Object> err = new HashMap<>();
            err.put("error", true);
            err.put("message", "Account is disabled");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }catch (Exception ex){
            Map<String, Object> err = new HashMap<>();
            err.put("error", true);
            err.put("message", "Authentication failed");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(err);
        }
   }

   @GetMapping("/profile")
   public ProfileResponse getProfile(@CurrentSecurityContext(expression = "authentication?.name") String email){
        return userService.getProfile(email);
   }

   @GetMapping("/isAuthenticated")
   public ResponseEntity<Boolean> isAuthenticated(@CurrentSecurityContext(expression = "authentication?.name") String email){
        return ResponseEntity.ok(email != null);
   }

   @PostMapping("send-reset-otp")
   public ResponseEntity<?> sendResetOtp(@RequestParam String email){
        try{
            userService.sendResetOtp(email);
            return ResponseEntity.status(HttpStatus.OK).body("OTP Send Successfully");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
   }

   @PostMapping("/reset-password")
   public ResponseEntity<String> resetPassword(@Valid @RequestBody ResetPasswordRequest request){
        try{
            userService.resetPassword(request.getEmail(), request.getOtp(), request.getNewPassword());
            return ResponseEntity.status(HttpStatus.OK).body("Password Reset Successfully");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
   }

   @PostMapping("/send-otp-verify-email")
   public ResponseEntity<?> sendOtpToVerifyEmail(@CurrentSecurityContext(expression = "authentication?.name") String email){
        try{
            userService.sendOtpToVerifyEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body("OTP Send Successfully");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
   }

   @PostMapping("/verify-email")
   public ResponseEntity<?> verifyEmailWithOtp(@RequestBody Map<String, Object> request,
                                               @CurrentSecurityContext(expression = "authentication?.name") String email){
        try{
            if(request.get("otp").toString() == null || request.get("otp").toString().isEmpty()){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP is required");
            }
            userService.verifyEmailOtp(email, request.get("otp").toString());
            return ResponseEntity.status(HttpStatus.OK).body("Email Verified Successfully");
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
   }
}
