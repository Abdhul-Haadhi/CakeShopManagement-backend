package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.EmployeeDto;
import com.example.CakeShopManagement.dto.EmployeeReportDto;
import com.example.CakeShopManagement.dto.UpdateEmployeeAccDto;
import com.example.CakeShopManagement.entity.EmployeeEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.entity.RoleEntity;
import com.example.CakeShopManagement.entity.UserEntity;
import com.example.CakeShopManagement.enums.UserRole;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.EmployeeMapper;
import com.example.CakeShopManagement.repository.EmployeeRepository;
import com.example.CakeShopManagement.repository.RoleRepository;
import com.example.CakeShopManagement.repository.UserRepository;
import com.example.CakeShopManagement.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    private final PasswordEncoder passwordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public EmployeeDto addEmployee(EmployeeDto employeeDto) {

        if(employeeRepository.existsByEmail(employeeDto.getEmail())) {
            throw new AppException("Email already exists.", HttpStatus.BAD_REQUEST);
        }
        if(employeeRepository.existsByPhone(employeeDto.getPhone())){
            throw new AppException("Phone number already exists.", HttpStatus.BAD_REQUEST);
        }
        EmployeeEntity employeeEntity = employeeMapper.toEmployeeEntity(employeeDto);

        if(employeeDto.getRoleId() != null){
            RoleEntity role = roleRepository.findById(employeeDto.getRoleId()).orElseThrow(()-> new RuntimeException("Role not found."));
            employeeEntity.setRole(role);
        }

        employeeEntity.setJoinDate(java.time.LocalDate.now());

        EmployeeEntity saveItem = employeeRepository.save(employeeEntity);

        EmployeeDto savedDto = employeeMapper.toEmployeeDto(saveItem);

//        savedDto.setJoinDate(java.time.LocalDate.now());
//
//        System.out.println("**************"+savedDto);

        return savedDto;


    }

    public void createEmployeeLogin(EmployeeDto employeeDto) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeDto.getEmployeeId())
                .orElseThrow(()->new RuntimeException("Employee not found"));

        Optional<UserEntity> existingUser = userRepository.findFirstByEmail(employeeEntity.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("This employee account already exists");
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setUsername(employeeDto.getUserName());
        userEntity.setEmail(employeeEntity.getEmail());
        userEntity.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
//        userEntity.setRole(UserRole.EMPLOYEE);
//        RoleEntity role =roleRepository.findById(employeeDto.getRoleId()).orElseThrow(() -> new RuntimeException("Role not found"));

        RoleEntity role = roleRepository.findById(employeeDto.getRoleId()).orElseThrow(()->new RuntimeException("Role not found."));

        userEntity.setRole(role);

        UserEntity savedUser = userRepository.save(userEntity);
        employeeEntity.setUser(savedUser);
        employeeRepository.save(employeeEntity);

    }

    public List<EmployeeDto> getEmployees(){
        try {
            List<EmployeeEntity> employeeEntityList = employeeRepository.findAll();
            List<EmployeeDto> employeeDtoList = employeeMapper.toEmployeeDtoList(employeeEntityList);
            return employeeDtoList;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    public EmployeeDto getEmployeeById(Long employeeId) {
//        try {
//            Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(employeeId);
//
//            if(!optionalEmployee.isPresent()) {
//                throw new RuntimeException("Employee not found");
//            }
//            return employeeMapper.toEmployeeDto(optionalEmployee.get());
//        }
//        catch (Exception e){
//            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public EmployeeDto getEmployeeById(Long employeeId){
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId).orElseThrow(()->new RuntimeException("Employee not found"));

        EmployeeDto dto = employeeMapper.toEmployeeDto(employeeEntity);

        if(employeeEntity.getUser()!=null){
            dto.setEmail(employeeEntity.getUser().getEmail());
            dto.setUserName(employeeEntity.getUser().getUsername());
        }
        dto.setPassword(null);
        return dto;
    }

    @Override
    public List<EmployeeReportDto> getEmployeeReport(Long roleId, Boolean activeOnly) {
        boolean isOnlyActive = activeOnly != null && activeOnly;
        List<EmployeeEntity> employees = employeeRepository.findEmployeeForReport(roleId, isOnlyActive);

        return employees.stream().map(emp -> {
            String status = (emp.getUser() != null) ? "Active" : "Pending Login";
            String roleName = (emp.getUser() != null) ? emp.getUser().getRole().getRoleName() : "N/A";

            return new EmployeeReportDto(
                    emp.getEmployeeId(),
                    emp.getEmployeeName(),
                    emp.getEmail(),
                    roleName,
                    emp.getPhone(),
                    status,
                    emp.getJoinDate()
            );
        }).collect(Collectors.toList());
    }

//    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
//
//        Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(employeeId);
//        if(!optionalEmployeeEntity.isPresent()){
//            throw new AppException("Employee does not Exists", HttpStatus.BAD_REQUEST);
//        }
//        EmployeeEntity newEmployeeEntity = employeeMapper.toEmployeeEntity(employeeDto);
//
//        if(employeeDto.getRoleId() != null){
//            RoleEntity role = roleRepository.findById(employeeDto.getRoleId()).orElseThrow(()-> new RuntimeException("Role not found."));
//            newEmployeeEntity.setRole(role);
//        }
//
//        newEmployeeEntity.setEmployeeId(employeeId);
//        newEmployeeEntity.setJoinDate(java.time.LocalDate.now());
//
//        if(employeeRepository.existsByEmailAndEmployeeIdNot(employeeDto.getEmail(), employeeId)){
//            throw new AppException("Email already exists.", HttpStatus.BAD_REQUEST);
//        }
//
//        if(employeeRepository.existsByPhoneAndEmployeeIdNot(employeeDto.getPhone(), employeeId)){
//            throw new AppException("Phone number already exists.", HttpStatus.BAD_REQUEST);
//        }
//
//        EmployeeEntity employeeEntity = employeeRepository.save(newEmployeeEntity);
//        EmployeeDto responseEmployeeDto = employeeMapper.toEmployeeDto(employeeEntity);
//        return responseEmployeeDto;
//
//
//    }

    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto){
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId).orElseThrow(()->new RuntimeException("Employee not found"));

        if(employeeRepository.existsByEmailAndEmployeeIdNot(employeeDto.getEmail(), employeeId)){
            throw new AppException("Email already exists.", HttpStatus.BAD_REQUEST);
        }
        if(employeeRepository.existsByPhoneAndEmployeeIdNot(employeeDto.getPhone(), employeeId)){
            throw new AppException("Phone number already exists.", HttpStatus.BAD_REQUEST);
        }

        existingEmployee.setEmployeeName(employeeDto.getEmployeeName());
        existingEmployee.setEmail(employeeDto.getEmail());
        existingEmployee.setPhone(employeeDto.getPhone());
        existingEmployee.setAddress(employeeDto.getAddress());

        if(employeeDto.getRoleId() != null){
            RoleEntity role = roleRepository.findById(employeeDto.getRoleId()).orElseThrow(()->new RuntimeException("Role not found."));

            existingEmployee.setRole(role);

            if(existingEmployee.getUser() != null){
                UserEntity linkedUser = existingEmployee.getUser();
                linkedUser.setRole(role);

                userRepository.save(linkedUser);
            }
        }
        EmployeeEntity savedEmployeeEntity = employeeRepository.save(existingEmployee);
        return employeeMapper.toEmployeeDto(savedEmployeeEntity);
    }

    public void updateEmployeePassword(UpdateEmployeeAccDto updateEmployeeAccDto){
        UserEntity userEntity = userRepository.findFirstByEmail(updateEmployeeAccDto.getEmail()).orElseThrow(()->new RuntimeException("User not found"));

        if(!passwordEncoder.matches(updateEmployeeAccDto.getCurrentPassword(),userEntity.getPassword())){
            throw new RuntimeException("Current password is incorrect");
        }
        userEntity.setPassword(passwordEncoder.encode(updateEmployeeAccDto.getNewPassword()));
        userRepository.save(userEntity);
    }

//    public EmployeeDto deleteEmployee(long employeeId) {
//        try {
//            Optional<EmployeeEntity> optionalEmployeeEntity = employeeRepository.findById(employeeId);
//            if(!optionalEmployeeEntity.isPresent()){
//                throw new AppException("Employee Registration does not Exists", HttpStatus.BAD_REQUEST);
//            }
//            employeeRepository.deleteById(employeeId);
//            return employeeMapper.toEmployeeDto(optionalEmployeeEntity.get());
//        }
//        catch (Exception e) {
//            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
    public boolean deleteEmployee(Long employeeId) {
        Optional<EmployeeEntity> optionalEmployee = employeeRepository.findById(employeeId);
        if(optionalEmployee.isPresent()) {

            EmployeeEntity employeeEntity = optionalEmployee.get();

//            if(employeeEntity.getUser() != null){
//                Long userId = employeeEntity.getUser().getUserId();
//
//                employeeEntity.setUser(null);
//                employeeRepository.save(employeeEntity);
//
//                userRepository.deleteById(userId);
//            }

            employeeRepository.deleteById(employeeId);
            return true;
        }
        return false;
    }
}
