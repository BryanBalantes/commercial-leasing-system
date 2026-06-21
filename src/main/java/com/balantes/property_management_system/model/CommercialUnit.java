package com.balantes.property_management_system.model;

import com.balantes.property_management_system.dto.CommercialUnitDTO;
import com.balantes.property_management_system.model.enums.CommercialUnitStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "commercial_unit")
public class CommercialUnit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "unit_name", nullable = false, unique = true)
    private String unitName;

    @Column(name = "monthly_rent")
    private Double monthlyRent;

    @Enumerated(EnumType.STRING)
    private CommercialUnitStatus status;

    public CommercialUnit() {

    }

    public CommercialUnit(CommercialUnitDTO dto) {
        this.id = dto.getId();
        this.unitName = dto.getUnitName();
        this.monthlyRent = dto.getMonthlyRent();
        this.status = dto.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public CommercialUnitStatus getStatus() {
        return status;
    }

    public void setStatus(CommercialUnitStatus status) {
        this.status = status;
    }
}




