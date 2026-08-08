package com.example.CakeShopManagement.repository;

import com.example.CakeShopManagement.entity.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Long> {
    Optional<EmployeeEntity> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByEmailAndEmployeeIdNot(String email, Long employeeId);
    boolean existsByPhoneAndEmployeeIdNot(String phone, Long employeeId);

    @Query("SELECT e FROM EmployeeEntity e "+
            "LEFT JOIN FETCH e.role r " +
            "LEFT JOIN FETCH e.user u " +
            "WHERE (:roleId is null or r.roleId = :roleId)"+
            "AND (:activeOnly IS FALSE OR u.userId IS NOT NULL)"
    )
    List<EmployeeEntity> findEmployeeForReport(@Param("roleId") Long roleId, @Param("activeOnly") Boolean activeOnly);
}
