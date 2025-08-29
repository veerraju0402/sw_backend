package com.example.demo.repositry;


import com.example.demo.entity.SoftwareInventory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SoftwareInventoryRepo extends JpaRepository<SoftwareInventory, Long> {

    List<SoftwareInventory> findBySoftwareNameContainingIgnoreCase(String softwareName);

    @Query("SELECT s FROM SoftwareInventory s WHERE LOWER(s.softwareName) LIKE LOWER(CONCAT('%', :name, '%'))")
    Page<SoftwareInventory> findBySoftwareName(@Param("name") String name, Pageable pageable);

    Page<SoftwareInventory> findBySoftwareCategory(String softwareCategory, Pageable pageable);

    @Query("SELECT s FROM SoftwareInventory s WHERE " +
            "LOWER(s.softwareName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.softwareManufacturer) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.softwareProduct) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.softwareCategoryGroup) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.softwareCategory) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
            "LOWER(s.softwareSubcategory) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<SoftwareInventory> searchSoftware(@Param("searchTerm") String searchTerm, Pageable pageable);

}