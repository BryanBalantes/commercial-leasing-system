package com.balantes.property_management_system.dto;

import com.balantes.property_management_system.model.Lease;
import com.balantes.property_management_system.model.enums.CommercialUnitStatus;
import com.balantes.property_management_system.model.enums.LeaseStatus;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

public class LeaseDTO {

    private Integer id;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double monthlyRental;

    private LocalDate entryDate;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double oneMonthAdvance;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double oneMonthDeposit;

    private Integer commercialUnitId;
    private String unitName;
    private Double unitMonthlyRent;
    private CommercialUnitStatus unitStatus;

    private String receiptImageUrl;

    private String cloudinaryImageId;

    private LeaseStatus status;

    private UserDTO user;
    private Integer userId;

    public LeaseDTO() {}

    public LeaseDTO(Lease model){
        this.id = model.getId();
        this.monthlyRental = model.getMonthlyRental();
        this.entryDate = model.getEntryDate();
        this.oneMonthAdvance = model.getOneMonthAdvance();
        this.oneMonthDeposit = model.getOneMonthDeposit();
        this.status = model.getStatus();
        this.receiptImageUrl = model.getReceiptImageUrl();
        this.cloudinaryImageId = model.getCloudinaryImageId();

        if (model.getUser() != null) {
            this.user = new UserDTO(model.getUser());
            this.userId = model.getUser().getId();
        }

        if (model.getCommercialUnit() != null) {
            this.commercialUnitId = model.getCommercialUnit().getId();
            this.unitName = model.getCommercialUnit().getUnitName();
            this.unitMonthlyRent = model.getCommercialUnit().getMonthlyRent();
            this.unitStatus = model.getCommercialUnit().getStatus();
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getMonthlyRental() {
        return monthlyRental;
    }

    public void setMonthlyRental(Double monthlyRental) {
        this.monthlyRental = monthlyRental;
    }

    public LocalDate getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(LocalDate entryDate) {
        this.entryDate = entryDate;
    }

    public Double getOneMonthAdvance() {
        return oneMonthAdvance;
    }

    public void setOneMonthAdvance(Double oneMonthAdvance) {
        this.oneMonthAdvance = oneMonthAdvance;
    }

    public Double getOneMonthDeposit() {
        return oneMonthDeposit;
    }

    public void setOneMonthDeposit(Double oneMonthDeposit) {
        this.oneMonthDeposit = oneMonthDeposit;
    }

    public Integer getCommercialUnitId() {
        return commercialUnitId;
    }

    public void setCommercialUnitId(Integer commercialUnitId) {
        this.commercialUnitId = commercialUnitId;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Double getUnitMonthlyRent() {
        return unitMonthlyRent;
    }

    public void setUnitMonthlyRent(Double unitMonthlyRent) {
        this.unitMonthlyRent = unitMonthlyRent;
    }

    public CommercialUnitStatus getUnitStatus() {
        return unitStatus;
    }

    public void setUnitStatus(CommercialUnitStatus unitStatus) {
        this.unitStatus = unitStatus;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public LeaseStatus getStatus() {
        return status;
    }

    public void setStatus(LeaseStatus status) {
        this.status = status;
    }

    public String getReceiptImageUrl() {
        return receiptImageUrl;
    }

    public void setReceiptImageUrl(String receiptImageUrl) {
        this.receiptImageUrl = receiptImageUrl;
    }

    public String getCloudinaryImageId() {
        return cloudinaryImageId;
    }

    public void setCloudinaryImageId(String cloudinaryImageId) {
        this.cloudinaryImageId = cloudinaryImageId;
    }

    // formatted for monthlyRental
    public String getFormattedMonthlyRental() {
        if (unitMonthlyRent == null) return "";
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return format.format(unitMonthlyRent);
    }

    // formatted for oneMonthAdvance
    public String getFormattedAdvance() {
        if (oneMonthAdvance == null) return "";
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return format.format(oneMonthAdvance);
    }

    // formatted for oneMonthDeposit
    public String getFormattedDeposit() {
        if (oneMonthDeposit == null) return "";
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return format.format(oneMonthDeposit);
    }
}
