package com.balantes.property_management_system.service;

import com.balantes.property_management_system.dto.TenantPaymentRecordDTO;
import com.balantes.property_management_system.model.Lease;
import com.balantes.property_management_system.model.TenantPaymentRecord;
import com.balantes.property_management_system.model.User;
import com.balantes.property_management_system.repository.LeaseRepository;
import com.balantes.property_management_system.repository.TenantPaymentRecordRepository;
import com.balantes.property_management_system.repository.UserRepository;
import com.balantes.property_management_system.response.CloudinaryResponse;
import com.balantes.property_management_system.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Service
public class TenantPaymentRecordService {

    private final TenantPaymentRecordRepository repo;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final LeaseRepository leaseRepository;

    public TenantPaymentRecordService(TenantPaymentRecordRepository repo,
                                      UserRepository userRepository,
                                      CloudinaryService cloudinaryService, LeaseRepository leaseRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.leaseRepository = leaseRepository;
    }

    public void create(TenantPaymentRecordDTO dto, MultipartFile file) {

        User user = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Lease lease = leaseRepository.findById(dto.getLeaseId())
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        TenantPaymentRecord model = new TenantPaymentRecord(dto, user);

        model.setLease(lease); // 🔥 THIS FIXES YOUR ISSUE

        repo.save(model);

        if (file != null && !file.isEmpty()) {
            uploadAndAttach(file, model);
            repo.save(model);
        }
    }

    // REST CREATE (no file)
    public void create(TenantPaymentRecordDTO dto) {
        User user = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        repo.save(new TenantPaymentRecord(dto, user));
    }

    // UPDATE (with file)
    public void update(TenantPaymentRecordDTO dto, MultipartFile file) {

        TenantPaymentRecord model = repo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        User user = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Lease lease = leaseRepository.findById(dto.getLeaseId())
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        model.setPeriod(dto.getPeriod());
        model.setAmount(dto.getAmount());
        model.setDate(dto.getDate());
        model.setStatus(dto.getStatus());
        model.setBalance(dto.getBalance());
        model.setAssignee(user);
        model.setLease(lease);

        if (file != null && !file.isEmpty()) {
            uploadAndAttach(file, model);
        }

        repo.save(model);
    }

    // REST UPDATE (no file)
    public void update(TenantPaymentRecordDTO dto) {

        TenantPaymentRecord model = repo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Record not found"));

        User user = userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.setPeriod(dto.getPeriod());
        model.setAmount(dto.getAmount());
        model.setDate(dto.getDate());
        model.setStatus(dto.getStatus());
        model.setBalance(dto.getBalance());
        model.setAssignee(user);

        repo.save(model);
    }

    public void delete(int id) {

        // 1. Find record in DB
        TenantPaymentRecord record = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found with id: " + id));

        // 2. Delete image from Cloudinary FIRST (if exists)
        if (record.getCloudinaryImageId() != null && !record.getCloudinaryImageId().isBlank()) {
            cloudinaryService.deleteFile(record.getCloudinaryImageId());
        }

        // 3. Delete record from database
        repo.delete(record);
    }

    // shared upload logic
    private void uploadAndAttach(MultipartFile file, TenantPaymentRecord model) {

        FileUploadUtil.assertAllowed(file);

        String original = file.getOriginalFilename();
        String fileName = FileUploadUtil.getFileName(original);

        CloudinaryResponse response = cloudinaryService.uploadFile(file, fileName);

        model.setImageUrl(response.getUrl());
        model.setCloudinaryImageId(response.getPublicId());
    }

    public void uploadImage(Integer id, MultipartFile file) {

        TenantPaymentRecord model = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));

        uploadAndAttach(file, model);

        repo.save(model);
    }

    public List<TenantPaymentRecordDTO> getAll() {
        return repo.findAll().stream().map(TenantPaymentRecordDTO::new).toList();
    }

    public TenantPaymentRecordDTO getById(int id) {
        return new TenantPaymentRecordDTO(
                repo.findById(id)
                        .orElseThrow(() -> new RuntimeException("Not found"))
        );
    }

    public List<TenantPaymentRecordDTO> getByUserId(int userId) {
        return repo.findByUserId(userId)
                .stream()
                .map(TenantPaymentRecordDTO::new)
                .toList();
    }
}
