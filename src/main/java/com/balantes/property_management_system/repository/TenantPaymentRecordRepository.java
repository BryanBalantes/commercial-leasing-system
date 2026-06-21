package com.balantes.property_management_system.repository;


import com.balantes.property_management_system.model.TenantPaymentRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TenantPaymentRecordRepository extends JpaRepository<TenantPaymentRecord, Integer> {

    @Query("SELECT r FROM TenantPaymentRecord r WHERE r.assignee.id = :userId")
    List<TenantPaymentRecord> findByUserId(@Param("userId") int userId);

    List<TenantPaymentRecord> findByAssignee_IdAndStatus(int userId, String status);
}
