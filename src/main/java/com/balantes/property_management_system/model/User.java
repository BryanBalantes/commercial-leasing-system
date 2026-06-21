package com.balantes.property_management_system.model;

import com.balantes.property_management_system.dto.UserDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false, length = 20)
    private String firstName;

    @Column(nullable = false, length = 20)
    private String lastName;

    @Column(nullable = false, length = 20)
    private String middleName;

    @Column(nullable = false, length = 200)
    private String address;

    @Column(nullable = false, unique = true)
    private String emailAddress;

    @Column(nullable = false, length = 11)
    private String contactNumber;

    @Column(nullable = false)
    private String password;

    private String profilePicUrl;

    private String cloudinaryProfilePicId;

    @Column(nullable = false)
    private String type;

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Lease> leases;

    @JsonIgnore
    @OneToMany(mappedBy = "assignee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<TenantPaymentRecord> paymentRecords;

    public User() {}

    public User(UserDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("UserDTO cannot be null");
        }
        this.firstName = dto.getFirstName();
        this.middleName = dto.getMiddleName();
        this.lastName = dto.getLastName();
        this.profilePicUrl = dto.getProfilePicUrl();
        this.cloudinaryProfilePicId = dto.getCloudinaryProfilePicId();
        this.address = dto.getAddress();
        this.emailAddress = dto.getEmailAddress();
        this.contactNumber = dto.getContactNumber();
        this.type = dto.getType();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getCloudinaryProfilePicId() {
        return cloudinaryProfilePicId;
    }

    public void setCloudinaryProfilePicId(String cloudinaryProfilePicId) {
        this.cloudinaryProfilePicId = cloudinaryProfilePicId;
    }
}
