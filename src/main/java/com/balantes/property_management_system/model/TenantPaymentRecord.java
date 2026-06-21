package com.balantes.property_management_system.model;

import com.balantes.property_management_system.dto.TenantPaymentRecordDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
public class TenantPaymentRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotNull(message = "Period is required")
    @DateTimeFormat(pattern = "yyyy-MM")
    private YearMonth period;

    @NotNull(message = "Amount is required")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double amount;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private LocalDateTime date;

    private String status;

    @NotNull(message = "Balance is required")
    @Min(value = 0, message = "Balance cannot be negative")
    private Double balance;

    //    @ManyToOne
//    @JoinColumn(name = "user_id")
//    private User assignee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User assignee;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lease_id")
    private Lease lease;

    private String imageUrl;

    private String cloudinaryImageId;

    public TenantPaymentRecord() {
    }

    public TenantPaymentRecord(TenantPaymentRecordDTO dto, User user) {
        this.period = dto.getPeriod();
        this.amount = dto.getAmount();
        this.date = dto.getDate();
        this.status = dto.getStatus();
        this.balance = dto.getBalance();
        this.assignee = user;
        this.imageUrl = dto.getImageUrl();
        this.cloudinaryImageId = dto.getCloudinaryImageId();

//        this.assignee = new User(dto.getAssignee());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public YearMonth getPeriod() {
        return period;
    }

    public void setPeriod(YearMonth period) {
        this.period = period;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public double getBalance() {
        return balance != null ? balance : 0.0;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public User getAssignee() {
        return assignee;
    }

    public void setAssignee(User assignee) {
        this.assignee = assignee;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCloudinaryImageId() {
        return cloudinaryImageId;
    }

    public void setCloudinaryImageId(String cloudinaryImageId) {
        this.cloudinaryImageId = cloudinaryImageId;
    }

    public Lease getLease() {
        return lease;
    }

    public void setLease(Lease lease) {
        this.lease = lease;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    // Add a formatted string getter
    public String getFormattedDate() {
        if (date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd h:mm a");
        return date.format(formatter);
    }

    // Add this for YearMonth
    public String getFormattedPeriod() {
        if (period == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy", java.util.Locale.ENGLISH);
        return period.format(formatter);
    }

    // formatted for amount
    public String getFormattedAmount() {
        if (amount == null) return "";
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return format.format(amount);
    }

    // formatted for balance
    public String getFormattedBalance() {
        if (balance == null) return "";
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return format.format(balance);
    }
}
