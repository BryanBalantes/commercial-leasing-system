package com.balantes.property_management_system.model;

import com.balantes.property_management_system.dto.LeaseDTO;
import com.balantes.property_management_system.model.enums.LeaseStatus;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "leases")
public class Lease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "monthly_rental", nullable = false)
    private Double monthlyRental;

    @Column(name = "entry_date", nullable = false)
    private LocalDate entryDate;

    @Column(name = "one_month_advance", nullable = false)
    private Double oneMonthAdvance;

    @Column(name = "one_month_deposit", nullable = false)
    private Double oneMonthDeposit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "commercial_unit_id", nullable = false)
    private CommercialUnit commercialUnit;

    private String receiptImageUrl;

    private String cloudinaryImageId;

    @Enumerated(EnumType.STRING)
    private LeaseStatus status; // ACTIVE, ENDED

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    public Lease() {}

    public Lease(LeaseDTO dto, User user, CommercialUnit unit) {
        this.monthlyRental = dto.getMonthlyRental();
        this.entryDate = dto.getEntryDate();
        this.oneMonthAdvance = dto.getOneMonthAdvance();
        this.oneMonthDeposit = dto.getOneMonthDeposit();
        this.commercialUnit = unit;
        this.status = dto.getStatus();
        this.user = user;
        this.receiptImageUrl = dto.getReceiptImageUrl();
        this.cloudinaryImageId = dto.getCloudinaryImageId();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public CommercialUnit getCommercialUnit() {
        return commercialUnit;
    }

    public void setCommercialUnit(CommercialUnit commercialUnit) {
        this.commercialUnit = commercialUnit;
    }

    public LeaseStatus getStatus() {
        return status;
    }

    public void setStatus(LeaseStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}

