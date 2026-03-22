package com.example.CakeShopManagement.service.auth;


import com.example.CakeShopManagement.dto.SignupRequest;
import com.example.CakeShopManagement.dto.UserDto;
import com.example.CakeShopManagement.entity.UserEntity;
import com.example.CakeShopManagement.enums.UserRole;
import com.example.CakeShopManagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public UserDto createUser(SignupRequest signupRequest) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(signupRequest.getEmail());
        userEntity.setUsername(signupRequest.getUsername());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
        userEntity.setRole(UserRole.CUSTOMER);
        UserEntity createdUser = userRepository.save(userEntity);

        UserDto userDto = new UserDto();
        userDto.setUserId(createdUser.getUserId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

    @PostConstruct
    public void createAdminAccount(){
        UserEntity adminAccount = userRepository.findByRole(UserRole.ADMIN);
        if(null == adminAccount){
            UserEntity userEntity = new UserEntity();
            userEntity.setEmail("admin@test.com");
            userEntity.setUsername("admin");
            userEntity.setRole(UserRole.ADMIN);
            userEntity.setPassword(bCryptPasswordEncoder.encode("Admin"));
            userRepository.save(userEntity);
        }

    }

}
