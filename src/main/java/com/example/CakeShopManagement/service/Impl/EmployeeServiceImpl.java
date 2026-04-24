package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.EmployeeDto;
import com.example.CakeShopManagement.entity.EmployeeEntity;
import com.example.CakeShopManagement.entity.ProductEntity;
import com.example.CakeShopManagement.entity.UserEntity;
import com.example.CakeShopManagement.enums.UserRole;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.EmployeeMapper;
import com.example.CakeShopManagement.repository.EmployeeRepository;
import com.example.CakeShopManagement.repository.UserRepository;
import com.example.CakeShopManagement.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;
    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository, EmployeeMapper employeeMapper, UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.employeeRepository = employeeRepository;
        this.employeeMapper = employeeMapper;
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }


    public EmployeeDto addEmployee(EmployeeDto employeeDto) {
        try {
            EmployeeEntity employeeEntity = employeeMapper.toEmployeeEntity(employeeDto);
            EmployeeEntity saveItem = employeeRepository.save(employeeEntity);
            EmployeeDto savedDto = employeeMapper.toEmployeeDto(saveItem);

//            System.out.println("**************"+savedDto);

            return savedDto;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
        userEntity.setPassword(bCryptPasswordEncoder.encode(employeeDto.getPassword()));
        userEntity.setRole(UserRole.EMPLOYEE);

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

    public EmployeeDto updateEmployee(Long employeeId, EmployeeDto employeeDto) {
        try {
            Optional<EmployeeEntity> optionalEmployeeEntity=employeeRepository.findById(employeeId);
            if(!optionalEmployeeEntity.isPresent()){
                throw new AppException("Employee Registration does not Exists", HttpStatus.BAD_REQUEST);
            }
            EmployeeEntity newEmployeeEntity = employeeMapper.toEmployeeEntity(employeeDto);
            newEmployeeEntity.setEmployeeId(employeeId);

            EmployeeEntity employeeEntity = employeeRepository.save(newEmployeeEntity);
            EmployeeDto responseEmployeeDto = employeeMapper.toEmployeeDto(employeeEntity);
            return responseEmployeeDto;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
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
