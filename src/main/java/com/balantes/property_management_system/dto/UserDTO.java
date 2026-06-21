package com.balantes.property_management_system.dto;


import com.balantes.property_management_system.model.User;
import com.balantes.property_management_system.validation.ConfirmPasswordInterface;
import com.balantes.property_management_system.validation.PasswordMatches;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

@PasswordMatches
public class UserDTO extends BaseDTO implements ConfirmPasswordInterface {

    private int id;

    @NotBlank(message = "First Name is required")
    @Size(min = 2, max = 20)
    private String firstName;

    @NotBlank(message = "Last Name is required")
    @Size(min = 2, max = 20)
    private String lastName;

    @NotBlank(message = "Middle Name is required")
    @Size(min = 2, max = 20)
    private String middleName;

    @NotBlank(message = "Address is required")
    @Size(min = 5, max = 200)
    private String address;

    @NotBlank(message = "Email Address is required")
    @Email(message = "Invalid email format")
    private String emailAddress;

    //  @NotBlank(message = "Contact Number is required")
//  @Pattern(
//          regexp = "^[0-9]{11}$",
//          message = "Contact number must be exactly 11 digits"
//  )
    private String contactNumber;

    //  @NotBlank(message = "Password   is required")
//  @Pattern(
//          regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,}$",
//          message = "Password must be at least 8 characters and include uppercase, lowercase, and number"
//  )
//  @JsonIgnore
    private String password;

    //  @JsonIgnore
    private String confirmPassword;

    @JsonIgnore private MultipartFile image;

    private String profilePicUrl;

    private String cloudinaryProfilePicId;

    private String type;

    private boolean active;

    public UserDTO() {}

    public UserDTO(User model) {
        this.id = model.getId();
        this.firstName = model.getFirstName();
        this.lastName = model.getLastName();
        this.middleName = model.getMiddleName();
        this.profilePicUrl = model.getProfilePicUrl();
        this.cloudinaryProfilePicId = model.getCloudinaryProfilePicId();
        this.address = model.getAddress();
        this.emailAddress = model.getEmailAddress();
        this.contactNumber = model.getContactNumber();
        this.type = model.getType();
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

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getFullName() {
        return firstName + " " + middleName + " " + lastName;
    }
}
