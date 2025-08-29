package com.example.demo.entity;


//import com.vladmihalcea.hibernate.type.json.JsonType;
import jakarta.persistence.*;
import lombok.Data;
//import org.hibernate.annotations.Type;

@Entity
@Table(name = "software_inventory")
@Data
public class SoftwareInventory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "software_name")
    private String softwareName;
    @Column(name = "software_product")
    private String softwareProduct;
    @Column(name = "software_manufacturer")
    private String softwareManufacturer;
    @Column(name = "software_category_group")
    private String softwareCategoryGroup;
    @Column(name = "software_category")
    private String softwareCategory;
    @Column(name = "software_subcategory")
    private String softwareSubcategory;
    @Column(name = "installs")
    private int installs;
    @Column(name = "is_licensable")
    private String isLicensable;

//    @Type(JsonBinaryType.class)
//    @JdbcTypeCode(SqlTypes.JSON)
//    @Column(name = "software_version", columnDefinition = "jsonb")

//    @Type(JsonType.class)
//    @Column(columnDefinition = "jsonb")
//    private SoftwareVersion softwareVersion;

}
