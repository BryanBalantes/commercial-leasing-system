package com.balantes.property_management_system.repository;

import com.balantes.property_management_system.model.CommercialUnit;
import com.balantes.property_management_system.model.enums.CommercialUnitStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommercialUnitRepository extends JpaRepository<CommercialUnit, Integer> {
    List<CommercialUnit> findByStatus(CommercialUnitStatus status);
}

