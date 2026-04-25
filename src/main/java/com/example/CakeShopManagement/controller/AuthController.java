package com.example.CakeShopManagement.controller;

import com.example.CakeShopManagement.dto.*;
import com.example.CakeShopManagement.entity.EmployeeEntity;
import com.example.CakeShopManagement.entity.UserEntity;
import com.example.CakeShopManagement.repository.EmployeeRepository;
import com.example.CakeShopManagement.repository.UserRepository;
import com.example.CakeShopManagement.service.EmployeeService;
import com.example.CakeShopManagement.service.Impl.EmployeeServiceImpl;
import com.example.CakeShopManagement.service.auth.AuthService;
import com.example.CakeShopManagement.service.auth.AuthServiceImpl;
import com.example.CakeShopManagement.utils.JwtUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Optional;

@RestController
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final EmployeeRepository employeeRepository;

    public static final String TOKEN_PREFIX = "Bearer ";
    public static final String HEADER_STRING = "Authorization";
    
    private final AuthService authService;
    private final AuthServiceImpl authServiceImpl;
    private final EmployeeService employeeService;
    private final EmployeeServiceImpl employeeServiceImpl;
    private PasswordEncoder passwordEncoder;


    public AuthController(AuthenticationManager authenticationManager, UserDetailsService userDetailsService, UserRepository userRepository, JwtUtil jwtUtil, EmployeeRepository employeeRepository, AuthService authService, AuthServiceImpl authServiceImpl, EmployeeService employeeService, EmployeeServiceImpl employeeServiceImpl) {
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.employeeRepository = employeeRepository;
        this.authService = authService;
        this.authServiceImpl = authServiceImpl;
        this.employeeService = employeeService;
        this.employeeServiceImpl = employeeServiceImpl;
    }


    @PostMapping("/authenticate")
    public void createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest, HttpServletResponse response) throws IOException, JSONException {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        }
        catch (BadCredentialsException e) {
            throw new BadCredentialsException("Incorrect username or password");
        }

        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        Optional<UserEntity> optionalUser = userRepository.findFirstByEmail(userDetails.getUsername());
        Optional<EmployeeEntity> employee = employeeRepository.findByEmail(optionalUser.get().getEmail());

        final String jwt = jwtUtil.generateToken(userDetails.getUsername());

        if(optionalUser.isPresent()){
            response.getWriter().write(new JSONObject()
                    .put("userId", optionalUser.get().getUserId())
                    .put("employeeId",employee.isPresent() ? employee.get().getEmployeeId() : null)
                    .put("email", optionalUser.get().getEmail())
                    .put("role", optionalUser.get().getRole())
                    .toString()
            );

            response.addHeader("Access-Control-Expose-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Authorization, X-PINGOTHER, Origin, " +
                    "X-Requested-With, Content-Type, Accept, X-Custom-header");

            response.addHeader(HEADER_STRING, TOKEN_PREFIX + jwt);
        }
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> signupUser(@RequestBody SignupRequest signupRequest) {
        if(authService.hasUserWithEmail(signupRequest.getEmail())){
            return new ResponseEntity<>("User already exist", HttpStatus.NOT_ACCEPTABLE);
        }

        UserDto userDto = authService.createUser(signupRequest);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @PutMapping("/api/admin/profile")
    public ResponseEntity<?> updateAdminProfile(@RequestBody UpdateProfileDto updateProfileDto) {

        System.out.println("****************Controller hit*****************");
        authServiceImpl.updateAdminProfile(updateProfileDto);
        return ResponseEntity.ok("Profile updated successfully");
    }

    @GetMapping("/api/employee/{employeeId}")
    public ResponseEntity<?> getEmployeeById(@PathVariable("employeeId") Long employeeId){

        return ResponseEntity.ok(employeeService.getEmployeeById(employeeId));
    }

    @PutMapping("/api/employee/change-password")
    public ResponseEntity<?> changeEmployeePassword(@RequestBody UpdateEmployeeAccDto updateProfileDto){
        try {
            employeeServiceImpl.updateEmployeePassword(updateProfileDto);
            return ResponseEntity.ok("Password updated successfully");
        }catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

//    @DeleteMapping("api/admin/employee-login/{userId}")
//    public ResponseEntity<Void> deleteEmployeeLogin(@PathVariable Long userId) {
//        boolean deleted = authService.deleteEmployeeLogin(userId);
//        if (deleted) {
//            return ResponseEntity.noContent().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
}
