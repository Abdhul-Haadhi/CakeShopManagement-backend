package com.example.CakeShopManagement.service.auth;

import com.example.CakeShopManagement.dto.SignupRequest;
import com.example.CakeShopManagement.dto.UpdateProfileDto;
import com.example.CakeShopManagement.dto.UserDto;

public interface AuthService {

    UserDto createUser(SignupRequest signupRequest);

    Boolean hasUserWithEmail(String email);

//    UpdateProfileDto updateAdminProfile(UpdateProfileDto updateProfileDto);
}
