package com.balantes.property_management_system.dto;

import com.balantes.property_management_system.model.CommercialUnit;
import com.balantes.property_management_system.model.enums.CommercialUnitStatus;

import java.text.NumberFormat;
import java.util.Locale;

public class CommercialUnitDTO {

    private Integer id;

    private String unitName;

    private Double monthlyRent;

    private CommercialUnitStatus status;

    public CommercialUnitDTO() {
    }

    public CommercialUnitDTO(CommercialUnit model) {
        this.id = model.getId();
        this.unitName = model.getUnitName();
        this.monthlyRent = model.getMonthlyRent();
        this.status = model.getStatus();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Double getMonthlyRent() {
        return monthlyRent;
    }

    public void setMonthlyRent(Double monthlyRent) {
        this.monthlyRent = monthlyRent;
    }

    public CommercialUnitStatus getStatus() {
        return status;
    }

    public void setStatus(CommercialUnitStatus status) {
        this.status = status;
    }

    // formatted for monthlyRental
    public String getFormattedMonthlyRental() {
        if (monthlyRent == null) return "";
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return format.format(monthlyRent);
    }
}

