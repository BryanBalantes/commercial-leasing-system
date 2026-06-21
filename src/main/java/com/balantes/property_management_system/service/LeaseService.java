package com.balantes.property_management_system.service;

import com.balantes.property_management_system.dto.LeaseDTO;
import com.balantes.property_management_system.model.CommercialUnit;
import com.balantes.property_management_system.model.Lease;
import com.balantes.property_management_system.model.User;
import com.balantes.property_management_system.model.enums.CommercialUnitStatus;
import com.balantes.property_management_system.model.enums.LeaseStatus;
import com.balantes.property_management_system.repository.CommercialUnitRepository;
import com.balantes.property_management_system.repository.LeaseRepository;
import com.balantes.property_management_system.repository.UserRepository;
import com.balantes.property_management_system.response.CloudinaryResponse;
import com.balantes.property_management_system.util.FileUploadUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeaseService {

    private final LeaseRepository repo;
    private final UserRepository userRepository;
    private final CloudinaryService cloudinaryService;
    private final CommercialUnitRepository commercialUnitRepository;

    public LeaseService(LeaseRepository repo,
                        UserRepository userRepository,
                        CloudinaryService cloudinaryService,
                        CommercialUnitRepository commercialUnitRepository) {
        this.repo = repo;
        this.userRepository = userRepository;
        this.cloudinaryService = cloudinaryService;
        this.commercialUnitRepository = commercialUnitRepository;
    }

    // CREATE (WITH OPTIONAL IMAGE)
    @Transactional
    public void create(LeaseDTO dto, MultipartFile file) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommercialUnit unit = commercialUnitRepository.findById(dto.getCommercialUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        if (unit.getStatus() != CommercialUnitStatus.VACANT) {
            throw new RuntimeException("Unit is not available");
        }

        unit.setStatus(CommercialUnitStatus.OCCUPIED);
        commercialUnitRepository.save(unit);

        Lease lease = new Lease(dto, user, unit);

        if (lease.getStatus() == null) {
            lease.setStatus(LeaseStatus.ACTIVE);
        }

        if (file != null && !file.isEmpty()) {
            uploadAndAttach(file, lease);
        }

        repo.save(lease);
    }

    // CREATE (NO FILE)
    public void create(LeaseDTO dto) {
        create(dto, null);
    }

    // GET ALL
    public List<LeaseDTO> getAll() {
        return repo.findAll()
                .stream()
                .map(LeaseDTO::new)
                .collect(Collectors.toList());
    }

    // GET BY ID
    public LeaseDTO getById(int id) {
        Lease lease = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        return new LeaseDTO(lease);
    }

    // GET BY USER (FIXED - USE QUERY METHOD)
    public List<LeaseDTO> getByUser(int userId) {
        return repo.findByUser_Id(userId)
                .stream()
                .map(LeaseDTO::new)
                .collect(Collectors.toList());
    }

    // UPDATE (WITH OPTIONAL IMAGE)
    @Transactional
    public void update(LeaseDTO dto, MultipartFile file) {

        Lease lease = repo.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        CommercialUnit newUnit = commercialUnitRepository.findById(dto.getCommercialUnitId())
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        CommercialUnit oldUnit = lease.getCommercialUnit();

        // CASE 1: if user changed unit
        if (oldUnit.getId() != newUnit.getId()) {

            // old unit becomes VACANT
            oldUnit.setStatus(CommercialUnitStatus.VACANT);
            commercialUnitRepository.save(oldUnit);

            // new unit must be available
            if (newUnit.getStatus() != CommercialUnitStatus.VACANT) {
                throw new RuntimeException("New unit is not available");
            }

            // new unit becomes OCCUPIED
            newUnit.setStatus(CommercialUnitStatus.OCCUPIED);
            commercialUnitRepository.save(newUnit);

            lease.setCommercialUnit(newUnit);
        }

        lease.setMonthlyRental(dto.getMonthlyRental());
        lease.setEntryDate(dto.getEntryDate());
        lease.setOneMonthAdvance(dto.getOneMonthAdvance());
        lease.setOneMonthDeposit(dto.getOneMonthDeposit());
        lease.setUser(user);
        lease.setStatus(dto.getStatus() != null ? dto.getStatus() : LeaseStatus.ACTIVE);

        if (file != null && !file.isEmpty()) {
            if (lease.getCloudinaryImageId() != null) {
                cloudinaryService.deleteFile(lease.getCloudinaryImageId());
            }
            uploadAndAttach(file, lease);
        }

        repo.save(lease);
    }

    // UPDATE (NO FILE)
    public void update(LeaseDTO dto) {
        update(dto, null);
    }

    // DELETE (WITH IMAGE CLEANUP)
    public void delete(int id) {

        Lease lease = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Lease not found"));

        // delete image first
        if (lease.getCloudinaryImageId() != null) {
            cloudinaryService.deleteFile(lease.getCloudinaryImageId());
        }

        repo.delete(lease);
    }

    // SHARED IMAGE LOGIC
    private void uploadAndAttach(MultipartFile file, Lease lease) {

        FileUploadUtil.assertAllowed(file);

        String fileName = FileUploadUtil.getFileName(file.getOriginalFilename());

        CloudinaryResponse response =
                cloudinaryService.uploadFile(file, fileName);

        lease.setReceiptImageUrl(response.getUrl());
        lease.setCloudinaryImageId(response.getPublicId());
    }

    public List<LeaseDTO> getActiveByUser(int userId) {
        return repo.findByUser_Id(userId)
                .stream()
                .filter(l -> l.getStatus() == LeaseStatus.ACTIVE)
                .map(LeaseDTO::new)
                .collect(Collectors.toList());
    }

    public LeaseDTO getActiveLeaseByUser(int userId) {

        return repo.findByUser_Id(userId)
                .stream()
                .filter(l -> l.getStatus() == LeaseStatus.ACTIVE)
                .findFirst()
                .map(LeaseDTO::new)
                .orElse(null);
    }
}