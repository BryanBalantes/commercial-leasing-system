package com.balantes.property_management_system.repository;

import com.balantes.property_management_system.model.CommercialUnit;
import com.balantes.property_management_system.model.Lease;
import com.balantes.property_management_system.model.enums.LeaseStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LeaseRepository extends JpaRepository<Lease, Integer> {
    List<Lease> findByUser_Id(int userId);

    List<Lease> findByCommercialUnitAndStatus(CommercialUnit commercialUnit, LeaseStatus status);

    boolean existsByUser_IdAndStatus(int userId, LeaseStatus status);
}
