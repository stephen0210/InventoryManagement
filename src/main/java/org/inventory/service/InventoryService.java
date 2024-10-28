package org.inventory.service;

import org.inventory.entity.Part;
import org.inventory.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InventoryService {
    @Autowired
    private PartRepository partRepository;

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part addPart(Part part) {
        return partRepository.save(part);
    }

    public Part updatePart(Long id, Part partDetails) {
        Part part = partRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Part not found"));

        part.setAvailableQty(partDetails.getAvailableQty());
        part.setThresholdLimit(partDetails.getThresholdLimit());
        part.setMinOrderQty(partDetails.getMinOrderQty());
        return partRepository.save(part);
    }
}
