package com.example.CakeShopManagement.service.auth;


import com.example.CakeShopManagement.dto.SignupRequest;
import com.example.CakeShopManagement.dto.UpdateProfileDto;
import com.example.CakeShopManagement.dto.UserDto;
import com.example.CakeShopManagement.entity.EmployeeEntity;
import com.example.CakeShopManagement.entity.RoleEntity;
import com.example.CakeShopManagement.entity.UserEntity;
import com.example.CakeShopManagement.enums.OrderStatus;
import com.example.CakeShopManagement.enums.UserRole;
import com.example.CakeShopManagement.repository.RoleRepository;
import com.example.CakeShopManagement.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public AuthServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }


//    @Autowired
//    private OrderRepository orderRepository;


    public UserDto createUser(SignupRequest signupRequest) {
        UserEntity userEntity = new UserEntity();

        userEntity.setEmail(signupRequest.getEmail());
        userEntity.setUsername(signupRequest.getUsername());
        userEntity.setPassword(new BCryptPasswordEncoder().encode(signupRequest.getPassword()));
//        userEntity.setRole(UserRole.CUSTOMER);
        RoleEntity customerRole = roleRepository.findByRoleName("CUSTOMER").orElseThrow(() -> new RuntimeException("Customer Role Not Found"));
        userEntity.setRole(customerRole);
        UserEntity createdUser = userRepository.save(userEntity);



//        OrderEntity orderEntity = new OrderEntity();
//        orderEntity.setAmount(0L);
//        orderEntity.setTotalAmount(0L);
//        orderEntity.setDiscount(0L);
//        orderEntity.setUserEntity(createdUser);
//        orderEntity.setOrderStatus(OrderStatus.PENDING);
//        orderRepository.save(orderEntity);



        UserDto userDto = new UserDto();
        userDto.setUserId(createdUser.getUserId());

        return userDto;
    }

    public Boolean hasUserWithEmail(String email) {
        return userRepository.findFirstByEmail(email).isPresent();
    }

//    @PostConstruct
//    public void createAdminAccount(){
//        Optional<UserEntity> adminAccount = userRepository.findFirstByRole_RoleName("ADMIN");
//        if(adminAccount.isEmpty()){
//            UserEntity userEntity = new UserEntity();
//            userEntity.setEmail("admin@test.com");
//            userEntity.setUsername("admin");
//            RoleEntity adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(()->new RuntimeException("Admin role not found"));
//            userEntity.setRole(adminRole);
//            userEntity.setPassword(new BCryptPasswordEncoder().encode("Admin"));
//            userRepository.save(userEntity);
//        }
//
//    }

    @PostConstruct
    public void createAdminAccount(){
        RoleEntity adminRole = roleRepository.findByRoleName("ADMIN").orElseThrow(() -> new RuntimeException("Admin Role Not Found"));

        if(userRepository.findFirstByRole_RoleName("ADMIN").isEmpty()){
            UserEntity userEntity = new UserEntity();

            userEntity.setEmail("admin@test.com");
            userEntity.setUsername("admin");
            userEntity.setRole(adminRole);
            userEntity.setPassword(new BCryptPasswordEncoder().encode("Admin"));

            userRepository.save(userEntity);
            System.out.println("Default admin account created");
        }
        else {
            System.out.println("Admin account already exists");
        }
    }

    public void updateAdminProfile(UpdateProfileDto updateProfileDto){
        UserEntity admin = userRepository.findFirstByRole_RoleName("ADMIN").orElseThrow(() -> new RuntimeException("Admin not found"));

        if(admin != null){
            if(new BCryptPasswordEncoder().matches(updateProfileDto.getCurrentPassword(), admin.getPassword())){
                admin.setEmail(updateProfileDto.getEmail());

                if(updateProfileDto.getNewPassword() != null && !updateProfileDto.getNewPassword().isEmpty()){
                    admin.setPassword(new BCryptPasswordEncoder().encode(updateProfileDto.getNewPassword()));
                }
                userRepository.save(admin);
            }
            else {
                throw new RuntimeException("Current password incorrect");
            }
        }
    }

//    public boolean deleteEmployeeLogin(Long userId) {
//        Optional<UserEntity> optionalUser = userRepository.findById(userId);
//        if(optionalUser.isPresent()) {
//            userRepository.deleteById(userId);
//            return true;
//        }
//        return false;
//    }

}
