package com.example.demo.service;

import com.example.demo.entity.SoftwareInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SoftwareInventoryFacade {
    
    Page<SoftwareInventory> getAllSoftwares(Pageable pageable);

    Page<SoftwareInventory> getAllSoftware(PageRequest of);

    Optional<SoftwareInventory> getSoftwareById(Long id);

    SoftwareInventory saveSoftware(SoftwareInventory software);

    SoftwareInventory updateSoftware(Long id, SoftwareInventory softwareDetails);

    boolean deleteSoftware(Long id);

    Page<SoftwareInventory> searchSoftware(String term, PageRequest of);

    Page<SoftwareInventory> getBysoftwareName(String manufacturer, PageRequest of);

    Page<SoftwareInventory> getSoftwareByCategory(String category, PageRequest of);
}
