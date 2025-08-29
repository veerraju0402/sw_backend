package com.example.demo;

import com.example.demo.entity.SoftwareInventory;
import com.example.demo.service.SoftwareInventoryFacade;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.StreamUtils;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@SpringBootApplication
public class DemoApplication {

    @Autowired
    private SoftwareInventoryFacade softwareInventoryFacade;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @PostConstruct
    public void importDataIfEmpty() {
        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM software_inventory", Integer.class);
        System.err.println("Count of software_inventory: " + count);
        if (count != null && count == 0) {
            try {
                System.err.println("Importing data from JSON file...");
                ClassPathResource resource = new ClassPathResource("software_inventory_202507181935.json");
                InputStream inputStream = resource.getInputStream();
                JsonFactory factory = new JsonFactory();
                JsonParser parser = factory.createParser(inputStream);
                int imported = 0;
                JsonToken firstToken = parser.nextToken();
                System.err.println("First JSON token: " + firstToken);
                if (firstToken != JsonToken.START_OBJECT) {
                    System.err.println("JSON file does not start with an object. Please check the file format.");
                }
                // Move to the start of the object
                if (firstToken == JsonToken.START_OBJECT) {
                    // Find the 'software_inventory' field
                    while (parser.nextToken() != JsonToken.FIELD_NAME && parser.currentToken() != null) {}
                    if ("software_inventory".equals(parser.getCurrentName())) {
                        parser.nextToken(); // Move to the array
                        if (parser.currentToken() == JsonToken.START_ARRAY) {
                            int preview = 0;
                            while (parser.nextToken() == JsonToken.START_OBJECT) {
                                if (preview < 3) {
                                    System.err.println("Previewing object " + (preview+1));
                                }
                                SoftwareInventory si = new SoftwareInventory();
                                Long id = null;
                                String softwareName = null;
                                String softwareProduct = null;
                                String softwareManufacturer = null;
                                String softwareCategoryGroup = null;
                                String softwareCategory = null;
                                String softwareSubcategory = null;
                                Integer installs = null;
                                String isLicensable = null;
                                while (parser.nextToken() != JsonToken.END_OBJECT) {
                                    String fieldName = parser.getCurrentName();
                                    parser.nextToken();
                                    String value = parser.getValueAsString();
                                    if (preview < 3) {
                                        System.err.println("  Field: " + fieldName + " = " + value);
                                    }
                                    switch (fieldName) {
                                        case "id":
                                            break;
                                        case "softwareName":
                                        case "software_name":
                                            softwareName = value;
                                            break;
                                        case "softwareProduct":
                                        case "software_product":
                                            softwareProduct = value;
                                            break;
                                        case "softwareManufacturer":
                                        case "software_manufacturer":
                                            softwareManufacturer = value;
                                            break;
                                        case "softwareCategoryGroup":
                                        case "software_category_group":
                                            softwareCategoryGroup = value;
                                            break;
                                        case "softwareCategory":
                                        case "software_category":
                                            softwareCategory = value;
                                            break;
                                        case "softwareSubcategory":
                                        case "software_subcategory":
                                            softwareSubcategory = value;
                                            break;
                                        case "installs":
                                            installs = parser.getIntValue();
                                            break;
                                        case "isLicensable":
                                        case "is_licensable":
                                            isLicensable = value;
                                            break;
                                        default:
                                            break;
                                    }
                                }
                                si.setId(id);
                                si.setSoftwareName(softwareName);
                                si.setSoftwareProduct(softwareProduct);
                                si.setSoftwareManufacturer(softwareManufacturer);
                                si.setSoftwareCategoryGroup(softwareCategoryGroup);
                                si.setSoftwareCategory(softwareCategory);
                                si.setSoftwareSubcategory(softwareSubcategory);
                                si.setInstalls(installs != null ? installs : 0);
                                si.setIsLicensable(isLicensable);
                                softwareInventoryFacade.saveSoftware(si);
                                imported++;
                                if (imported % 100 == 0) {
                                    System.err.println(imported + " records imported...");
                                }
                                preview++;
                            }
                        } else {
                            System.err.println("'software_inventory' field is not an array. Please check the file format.");
                        }
                    } else {
                        System.err.println("Could not find 'software_inventory' field in JSON object.");
                    }
                }
                parser.close();
                inputStream.close();
                System.err.println("Data import completed. Total records: " + imported);
            } catch (Exception e) {
                System.err.println("Failed to import data from JSON: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
