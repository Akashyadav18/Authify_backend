package com.Security.Authify.service;

import com.Security.Authify.io.ProfileRequest;
import com.Security.Authify.io.ProfileResponse;

public interface UserService {

    ProfileResponse createProfile(ProfileRequest request);

    ProfileResponse getProfile(String email);

    void sendResetOtp(String email);

    void resetPassword(String email, String otp, String newPassword);

    void sendOtpToVerifyEmail(String email);

    void verifyEmailOtp(String email, String otp);

    String getLoggedInUserId(String email);
}
