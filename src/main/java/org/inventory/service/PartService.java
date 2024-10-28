package org.inventory.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import org.inventory.entity.Part;
import org.inventory.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class PartService {

    @Autowired
    private Validator validator;

    @Autowired
    private PartRepository partRepository;

    public List<Part> parseCSV(MultipartFile file) throws IOException {
        List<Part> parts = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                Part part = new Part();
                part.setId(Long.parseLong(values[0])); //ID
                part.setName(values[1]); //name
                part.setAvailableQty(Integer.parseInt(values[2])); //available qty
                part.setMinOrderQty(Integer.parseInt(values[3])); //min qty
                validatePart(part);
                parts.add(part);
            }
        }
        return parts;
    }

    private void validatePart(Part part) {
        Set<ConstraintViolation<Part>> violations = validator.validate(part);
        if (!violations.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (ConstraintViolation<Part> violation : violations) {
                sb.append(violation.getMessage()).append(" ");
            }
            throw new IllegalArgumentException("Validation errors: " + sb.toString().trim());
        }
    }

    public void updateParts(List<Part> parts) {
        partRepository.saveAll(parts);
    }

    public Part updateAvailableQty(Long id, int newQty) {
        Optional<Part> partOptional = partRepository.findById(id);
        if (partOptional.isPresent()) {
            Part part = partOptional.get();
            part.setAvailableQty(newQty);
            return partRepository.save(part);
        }
        return null;
    }
}
