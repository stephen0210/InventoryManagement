package org.inventory.repository;

import org.inventory.entity.Part;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PartRepository extends JpaRepository<Part, Long> {
    List<Part> findByAvailableQtyLessThan(int thresholdLimit);
}

