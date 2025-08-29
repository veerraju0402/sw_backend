package com.example.demo.controller;


import com.example.demo.entity.SoftwareInventory;
import com.example.demo.service.SoftwareInventoryFacade;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import jakarta.validation.Valid;

import java.util.Optional;

@RestController
@RequestMapping("/api/software")
@CrossOrigin(origins = "http://localhost:3000")
@Tag(name = "Software API", description = "API for managing software inventory")
public class TestController {

    @Autowired
    private SoftwareInventoryFacade softwareInventoryFacade;

    @GetMapping
    @Operation(summary = "Get all software", description = "Retrieve a paginated list of all software in the inventory")
    public ResponseEntity<Page<SoftwareInventory>> getAllSoftware(
            @Parameter(description = "Page number, starting from 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {
        Page<SoftwareInventory> softwarePage = softwareInventoryFacade.getAllSoftware(PageRequest.of(page, size));
        return ResponseEntity.ok(softwarePage);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get software by ID", description = "Retrieve details of a specific software by its ID")
    public ResponseEntity<SoftwareInventory> getSoftwareById(@Parameter(description = "ID of the software") @PathVariable Long id) {
        Optional<SoftwareInventory> software = softwareInventoryFacade.getSoftwareById(id);
        return software.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(summary = "Create new software", description = "Add a new software entry to the inventory")
    public ResponseEntity<SoftwareInventory> createSoftware(@Valid @RequestBody SoftwareInventory software) {
        software.setId(null); // Ensure id is null so JPA will auto-generate it
        SoftwareInventory savedSoftware = softwareInventoryFacade.saveSoftware(software);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedSoftware);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update software", description = "Update details of an existing software")
    public ResponseEntity<SoftwareInventory> updateSoftware(
            @Parameter(description = "ID of the software to update") @PathVariable Long id,
            @Valid @RequestBody SoftwareInventory softwareDetails) {
        SoftwareInventory updatedSoftware = softwareInventoryFacade.updateSoftware(id, softwareDetails);
        if (updatedSoftware != null) {
            return ResponseEntity.ok(updatedSoftware);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete software", description = "Delete a software entry by its ID")
    public ResponseEntity<Void> deleteSoftware(@Parameter(description = "ID of the software to delete") @PathVariable Long id) {
        boolean deleted = softwareInventoryFacade.deleteSoftware(id);
        if (deleted) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/search")
    @Operation(summary = "Search software", description = "Search for software by a term")
    public ResponseEntity<Page<SoftwareInventory>> searchSoftware(
            @Parameter(description = "Search term") @RequestParam String term,
            @Parameter(description = "Page number, starting from 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {
        Page<SoftwareInventory> searchResults = softwareInventoryFacade.searchSoftware(term, PageRequest.of(page, size));
        return ResponseEntity.ok(searchResults);
    }

    @GetMapping("/software/{name}")
    @Operation(summary = "Get software by name", description = "Retrieve software by software name")
    public ResponseEntity<Page<SoftwareInventory>> getSoftwareByManufacturer(
            @Parameter(description = "software name") @PathVariable String name,
            @Parameter(description = "Page number, starting from 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {
        Page<SoftwareInventory> softwareList = softwareInventoryFacade.getBysoftwareName(name, PageRequest.of(page, size));
        return ResponseEntity.ok(softwareList);
    }

    @GetMapping("/category/{category}")
    @Operation(summary = "Get software by category", description = "Retrieve software by category")
    public ResponseEntity<Page<SoftwareInventory>> getSoftwareByCategory(
            @Parameter(description = "Category name") @PathVariable String category,
            @Parameter(description = "Page number, starting from 0") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Number of items per page") @RequestParam(defaultValue = "10") int size) {
        Page<SoftwareInventory> softwareList = softwareInventoryFacade.getSoftwareByCategory(category, PageRequest.of(page, size));
        return ResponseEntity.ok(softwareList);
    }

    /// //

    @GetMapping("/test")
    public String getWelcomeMessage() {
        System.out.println("inside getWelcomeMessage");
        return "Welcome response";
    }
    @GetMapping("/getall")
    @Operation(summary = "Get all software (legacy)", description = "Retrieve a paginated list of all software in the inventory (legacy endpoint)")
    public Page<SoftwareInventory> getAllSoftwares(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return softwareInventoryFacade.getAllSoftwares(pageable);
    }

}
