package com.example.CakeShopManagement.config;

import com.example.CakeShopManagement.entity.PermissionEntity;
import com.example.CakeShopManagement.entity.RoleEntity;
import com.example.CakeShopManagement.repository.PermissionRepository;
import com.example.CakeShopManagement.repository.RoleRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class DatabaseInitializer {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    public DatabaseInitializer(RoleRepository roleRepository, PermissionRepository permissionRepository) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
    }

    @PostConstruct
    public void initializeDatabase(){

        // ==========================
        // Create Permissions
        // ==========================

        List<String> permissions = List.of(

                "DASHBOARD_MANAGEMENT",
                "ORDER_MANAGEMENT",

                "PERMISSION_MANAGEMENT",
                "ROLE_MANAGEMENT",

                "CATEGORY_MANAGEMENT",
                "PRODUCT_MANAGEMENT",
                "INVENTORY_MANAGEMENT",
                "RECIPE_MANAGEMENT",

                "EMPLOYEE_MANAGEMENT",
                "CUSTOMER_MANAGEMENT",

                "REPORT_MANAGEMENT"
        );


        for(String permissionName : permissions){

            if(permissionRepository.findByPermissionName(permissionName).isEmpty()){

                PermissionEntity permission = new PermissionEntity();

                permission.setPermissionName(permissionName);
                permission.setDescription(permissionName);

                permissionRepository.save(permission);
            }
        }



        // ==========================
        // Create ADMIN Role
        // ==========================

        RoleEntity admin;


        if(roleRepository.findByRoleName("ADMIN").isEmpty()){

            admin = new RoleEntity();

            admin.setRoleName("ADMIN");
            admin.setDescription("System Administrator");

        }
        else{

            admin = roleRepository.findByRoleName("ADMIN").get();

        }


        // Give all permissions to ADMIN

        admin.setPermissions(
                new java.util.HashSet<>(permissionRepository.findAll())
        );


        roleRepository.save(admin);



        // ==========================
        // Employee Role
        // ==========================


//        if(roleRepository.findByRoleName("EMPLOYEE").isEmpty()){
//
//            RoleEntity employee = new RoleEntity();
//
//            employee.setRoleName("EMPLOYEE");
//            employee.setDescription("Employee");
//
//            roleRepository.save(employee);
//        }

        // ---------- EMPLOYEE ----------
//        if(roleRepository.findByRoleName("EMPLOYEE").isEmpty()){
//
//            RoleEntity employee = new RoleEntity();
//            employee.setRoleName("EMPLOYEE");
//            employee.setDescription("Employee");
//
//            roleRepository.save(employee);
//        }
//
//        // ---------- CUSTOMER ----------
//        if(roleRepository.findByRoleName("CUSTOMER").isEmpty()){
//
//            RoleEntity customer = new RoleEntity();
//            customer.setRoleName("CUSTOMER");
//            customer.setDescription("Customer");
//
//            roleRepository.save(customer);
//        }
    }
}
