package com.example.CakeShopManagement.service.Impl;

import com.example.CakeShopManagement.dto.CustomerDto;
import com.example.CakeShopManagement.entity.CustomerEntity;
import com.example.CakeShopManagement.exceptions.AppException;
import com.example.CakeShopManagement.mappers.CustomerMapper;
import com.example.CakeShopManagement.repository.CustomerRepository;
import com.example.CakeShopManagement.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    public CustomerDto addCustomer(CustomerDto customerDto) {
        try {
            CustomerEntity customerEntity = customerMapper.toCustomerEntity(customerDto);
            CustomerEntity saveItem = customerRepository.save(customerEntity);
            CustomerDto savedDto = customerMapper.toCustomerDto(saveItem);

//            System.out.println("**************"+savedDto);

            return savedDto;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public List<CustomerDto> getCustomers() {
        try {
            List<CustomerEntity> customerEntityList = customerRepository.findAll();
            List<CustomerDto> customerDtoList = customerMapper.toCustomerDtoList(customerEntityList);
            return customerDtoList;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public CustomerDto updateCustomer(Long customerId, CustomerDto customerDto) {
        try {
            Optional<CustomerEntity> optionalCustomerEntity=customerRepository.findById(customerId);
            if(!optionalCustomerEntity.isPresent()){
                throw new AppException("Customer does not Exists", HttpStatus.BAD_REQUEST);
            }
            CustomerEntity newCustomerEntity = customerMapper.toCustomerEntity(customerDto);
            newCustomerEntity.setCustomerId(customerId);

            CustomerEntity customerEntity = customerRepository.save(newCustomerEntity);
            CustomerDto responseCustomerDto = customerMapper.toCustomerDto(customerEntity);
            return responseCustomerDto;
        }
        catch (Exception e) {
            throw new AppException("Request failed with error: "+e, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public boolean deleteCustomer(Long customerId) {
        Optional<CustomerEntity> optionalCustomer = customerRepository.findById(customerId);
        if(optionalCustomer.isPresent()) {

            CustomerEntity customerEntity = optionalCustomer.get();
            customerRepository.deleteById(customerId);
            return true;
        }
        return false;
    }

}
