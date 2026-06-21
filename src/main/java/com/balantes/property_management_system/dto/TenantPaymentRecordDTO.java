package com.balantes.property_management_system.dto;

import com.balantes.property_management_system.model.TenantPaymentRecord;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class TenantPaymentRecordDTO {
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

    private Integer assigneeId;
    private UserDTO assignee;

    private Integer leaseId;

    private String imageUrl;

    private String cloudinaryImageId;

    public TenantPaymentRecordDTO() {}

    public TenantPaymentRecordDTO(TenantPaymentRecord model) {
        this.id = model.getId();
        this.period = model.getPeriod();
        this.amount = model.getAmount();
        this.date = model.getDate();
        this.status = model.getStatus();
        this.balance = model.getBalance();
        this.imageUrl = model.getImageUrl();
        this.cloudinaryImageId = model.getCloudinaryImageId();
        if (model.getAssignee() != null) {
            this.assignee = new UserDTO(model.getAssignee());
            this.assigneeId = model.getAssignee().getId();
        }

        if (model.getLease() != null) {
            this.leaseId = model.getLease().getId();
        }

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

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public UserDTO getAssignee() {
        return assignee;
    }

    public void setAssignee(UserDTO assignee) {
        this.assignee = assignee;
    }

    public Integer getAssigneeId() {
        return assigneeId;
    }

    public void setAssigneeId(Integer assigneeId) {
        this.assigneeId = assigneeId;
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

    public Integer getLeaseId() {
        return leaseId;
    }

    public void setLeaseId(Integer leaseId) {
        this.leaseId = leaseId;
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

    // Add this for amount and balance
    public String getFormattedBalance() {
        if (balance == null) return "";
        NumberFormat format = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
        return format.format(balance);
    }
}
