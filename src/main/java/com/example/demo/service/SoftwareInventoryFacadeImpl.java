package com.example.demo.service;

import com.example.demo.entity.SoftwareInventory;
import com.example.demo.repositry.SoftwareInventoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

@Service
public class SoftwareInventoryFacadeImpl implements SoftwareInventoryFacade {
    private static final Logger log = LoggerFactory.getLogger(SoftwareInventoryFacadeImpl.class);

    @Autowired
    private SoftwareInventoryRepo softwareInventoryRepo;


    public Page<SoftwareInventory> getAllSoftwares(Pageable pageable) {
        log.info("fetching All softwares with pagination");
        return softwareInventoryRepo.findAll(pageable);
    }

    public Page<SoftwareInventory> getAllSoftware(PageRequest pageRequest) {
        log.info("fetching all software with pagination");
        return softwareInventoryRepo.findAll(pageRequest);
    }

    public Optional<SoftwareInventory> getSoftwareById(Long id) {
        log.info("fetching software by id: {}", id);
        return softwareInventoryRepo.findById(id);
    }

    public SoftwareInventory saveSoftware(SoftwareInventory software) {
        log.info("saving software: {}", software);
        return softwareInventoryRepo.save(software);
    }

    public SoftwareInventory updateSoftware(Long id, SoftwareInventory softwareDetails) {
        log.info("updating software with id: {}", id);
        return softwareInventoryRepo.findById(id)
                .map(existing -> {
                    existing.setSoftwareName(softwareDetails.getSoftwareName());
                    existing.setSoftwareProduct(softwareDetails.getSoftwareProduct());
                    existing.setSoftwareManufacturer(softwareDetails.getSoftwareManufacturer());
                    existing.setSoftwareCategoryGroup(softwareDetails.getSoftwareCategoryGroup());
                    existing.setSoftwareCategory(softwareDetails.getSoftwareCategory());
                    existing.setSoftwareSubcategory(softwareDetails.getSoftwareSubcategory());
                    existing.setInstalls(softwareDetails.getInstalls());
                    existing.setIsLicensable(softwareDetails.getIsLicensable());
                    return softwareInventoryRepo.save(existing);
                })
                .orElse(null);
    }

    public boolean deleteSoftware(Long id) {
        log.info("deleting software with id: {}", id);
        if (softwareInventoryRepo.existsById(id)) {
            softwareInventoryRepo.deleteById(id);
            return true;
        }
        return false;
    }

    public Page<SoftwareInventory> searchSoftware(String term, PageRequest pageRequest) {
        log.info("searching software with term: {}", term);
        // Example: search by software name or product (customize as needed)
        return softwareInventoryRepo.searchSoftware(term, pageRequest);
     }

    public Page<SoftwareInventory> getBysoftwareName(String name, PageRequest pageRequest) {
        log.info("fetching software by name: {}", name);
        // Example: filter by manufacturer (customize as needed)
        return softwareInventoryRepo.findBySoftwareName(name,pageRequest); // Replace with actual filter logic
    }

    public Page<SoftwareInventory> getSoftwareByCategory(String category, PageRequest pageRequest) {
        log.info("fetching software by category: {}", category);
        // Example: filter by category (customize as needed)
        return softwareInventoryRepo.findAll(pageRequest); // Replace with actual filter logic
    }

}

