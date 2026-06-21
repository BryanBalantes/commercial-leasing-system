package com.balantes.property_management_system.service;

import com.balantes.property_management_system.dto.AuthenticatedUserDTO;
import com.balantes.property_management_system.dto.UserDTO;
import com.balantes.property_management_system.model.User;
import com.balantes.property_management_system.repository.UserRepository;
import com.balantes.property_management_system.response.CloudinaryResponse;
import com.balantes.property_management_system.util.FileUploadUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;

    private final PasswordEncoder passwordEncoder;
    private final LeaseRepository leaseRepository;

    public UserService(
            UserRepository userRepository,
            CloudinaryService cloudinaryService,
            PasswordEncoder passwordEncoder,
            LeaseRepository leaseRepository
    ) {
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.passwordEncoder = passwordEncoder;
        this.leaseRepository = leaseRepository;
    }

    public List<UserDTO> getAll() {
        return userRepository.findAll()
                .stream()
                .map(user -> {
                    UserDTO dto = new UserDTO(user);
                    dto.setActive(isTenantActive(user));
                    return dto;
                })
                .toList();
    }

    public UserDTO getById(int id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));

        UserDTO dto = new UserDTO(user);
        dto.setActive(isTenantActive(user));

        return dto;
    }

    public void create(UserDTO userDTO) {
        System.out.println("CREATE METHOD CALLED");
        // 1. basic validation safeguard
        if (userDTO.getPassword() == null || userDTO.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }

        if (userDTO.getEmailAddress() == null || userDTO.getEmailAddress().isBlank()) {
            throw new RuntimeException("Email is required");
        }

        User model = new User(userDTO);

        model.setPassword(passwordEncoder.encode(userDTO.getPassword()));

        try {
            if (userDTO.getImage() != null && !userDTO.getImage().isEmpty()) {

                FileUploadUtil.assertAllowed(userDTO.getImage());

                String fileName =
                        FileUploadUtil.getFileName(userDTO.getImage().getOriginalFilename());

                CloudinaryResponse response =
                        cloudinaryService.uploadFile(userDTO.getImage(), fileName);

                model.setProfilePicUrl(response.getUrl());
                model.setCloudinaryProfilePicId(response.getPublicId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Image upload failed: " + e.getMessage());
        }

        userRepository.save(model);
    }

    public void update(UserDTO userDTO) {

        User existing = userRepository.findById(userDTO.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        existing.setFirstName(userDTO.getFirstName());
        existing.setLastName(userDTO.getLastName());
        existing.setMiddleName(userDTO.getMiddleName());
        existing.setAddress(userDTO.getAddress());
        existing.setContactNumber(userDTO.getContactNumber());
        existing.setEmailAddress(userDTO.getEmailAddress());

        try {
            if (userDTO.getImage() != null && !userDTO.getImage().isEmpty()) {

                if (existing.getCloudinaryProfilePicId() != null) {
                    cloudinaryService.deleteFile(existing.getCloudinaryProfilePicId());
                }

                FileUploadUtil.assertAllowed(userDTO.getImage());

                String fileName = FileUploadUtil.getFileName(
                        userDTO.getImage().getOriginalFilename()
                );

                CloudinaryResponse response =
                        cloudinaryService.uploadFile(userDTO.getImage(), fileName);

                existing.setProfilePicUrl(response.getUrl());
                existing.setCloudinaryProfilePicId(response.getPublicId());
            }

        } catch (Exception e) {
            throw new RuntimeException("Update failed: " + e.getMessage());
        }

        userRepository.save(existing);
    }

    public void delete(int id) {
        userRepository.deleteById(id);
    }

    private boolean isTenantActive(User user) {

        if (isAdmin(user)) {
            return false;
        }

        return leaseRepository.existsByUser_IdAndStatus(
                user.getId(),
                LeaseStatus.ACTIVE
        );
    }

    private boolean isAdmin(User user) {
        return "ADMIN".equalsIgnoreCase(user.getType());
    }

    private boolean hasActiveLease(User user) {
        return leaseRepository.existsByUser_IdAndStatus(
                user.getId(),
                LeaseStatus.ACTIVE
        );
    }

    private boolean isActiveUser(User user) {

        if (isAdmin(user)) {
            return false; // ADMIN excluded sa tenant logic
        }

        return hasActiveLease(user);
    }

    public List<UserDTO> getActiveUsers() {

        return userRepository.findAll()
                .stream()
                .filter(user -> !isAdmin(user))
                .filter(this::isActiveUser)
                .map(user -> {
                    UserDTO dto = new UserDTO(user);
                    dto.setActive(true);
                    return dto;
                })
                .toList();
    }

    public long countActiveTenants() {
        return userRepository.findAll()
                .stream()
                .filter(user -> !isAdmin(user))
                .filter(this::isTenantActive)
                .count();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByEmailAddress(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new AuthenticatedUserDTO(
                user.getEmailAddress(),
                user.getPassword(),
                user.getType(), // ✅ ito ang ROLE mo
                user.getFirstName() + " " + user.getLastName() // ✅ full name manually built
        );
    }

    public UserDTO getByEmail(String email) {

        User user = userRepository.findByEmailAddress(email);

        if (user == null) {
            throw new RuntimeException("User not found");
        }

        UserDTO dto = new UserDTO(user);
        dto.setActive(isTenantActive(user));

        return dto;
    }
}
