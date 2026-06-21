package com.balantes.property_management_system.service;

import com.balantes.property_management_system.dto.CommercialUnitDTO;
import com.balantes.property_management_system.model.CommercialUnit;
import com.balantes.property_management_system.model.enums.CommercialUnitStatus;
import com.balantes.property_management_system.repository.CommercialUnitRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommercialUnitService {

    private final CommercialUnitRepository repository;

    public CommercialUnitService(CommercialUnitRepository repository) {
        this.repository = repository;
    }

    public List<CommercialUnitDTO> getAllUnits() {
        return repository.findAll()
                .stream()
                .map(CommercialUnitDTO::new)
                .toList();
    }

    public CommercialUnit create(CommercialUnitDTO unitDTO) {

        try {
            CommercialUnit model = new CommercialUnit(unitDTO);
            return repository.save(model);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Unit name already exists");
        }
    }

    public CommercialUnit update(Integer id, CommercialUnitDTO dto) {
        CommercialUnit existing = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));
        existing.setUnitName(dto.getUnitName());
        existing.setStatus(dto.getStatus());
        return repository.save(existing);
    }

    public CommercialUnit findById(Integer id) {
        return repository.findById(id).orElse(null);
    }

    public void delete(Integer id) {
        repository.deleteById(id);
    }

    public long countUnits() {
        return repository.count();
    }

    public List<CommercialUnitDTO> getAvailableUnits() {
        return repository.findByStatus(CommercialUnitStatus.VACANT)
                .stream()
                .map(CommercialUnitDTO::new)
                .toList();
    }

    public List<CommercialUnitDTO> getUnitsForUpdate(int currentUnitId) {

        List<CommercialUnit> units = repository.findAll();

        return units.stream()
                .filter(u ->
                        u.getStatus() == CommercialUnitStatus.VACANT
                                || u.getId() == currentUnitId
                )
                .map(CommercialUnitDTO::new)
                .toList();
    }

    public CommercialUnitDTO getById(Integer id) {
        CommercialUnit unit = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Unit not found"));

        return new CommercialUnitDTO(unit);
    }

    //    Soft Delete
    public void softDelete(Integer id) {
        CommercialUnit unit = repository.findById(id)
                .orElseThrow();
        unit.setStatus(CommercialUnitStatus.DECOMMISSIONED);
        repository.save(unit);
    }
}

