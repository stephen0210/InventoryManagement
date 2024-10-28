package org.inventory.controller;

import org.inventory.entity.Part;
import org.inventory.service.InventoryService;
import org.inventory.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {
    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private PartService partService;

    @GetMapping("/parts")
    public List<Part> getAllParts() {
        return inventoryService.getAllParts();
    }

    @PostMapping("/parts")
    public Part addPart(@RequestBody Part part) {
        return inventoryService.addPart(part);
    }

    @PutMapping("/parts/{id}")
    public Part updatePart(@PathVariable Long id, @RequestBody Part part) {
        return inventoryService.updatePart(id, part);
    }

    @PostMapping("/parts/upload")
    public ResponseEntity<String> uploadParts(@RequestParam("file") MultipartFile file) {
        try {
            List<Part> parts = partService.parseCSV(file);
            partService.updateParts(parts);
            return ResponseEntity.ok("Parts uploaded successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error uploading parts: " + e.getMessage());
        }
    }

    @PutMapping("/{id}/available-qty")
    public ResponseEntity<Part> updateAvailableQty(@PathVariable Long id, @RequestBody int newQty) {
        Part updatedPart = partService.updateAvailableQty(id, newQty);
        if (updatedPart != null) {
            return ResponseEntity.ok(updatedPart);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}


