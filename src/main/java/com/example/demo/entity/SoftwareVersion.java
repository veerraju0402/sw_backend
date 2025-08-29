package com.example.demo.entity;

import lombok.Data;

@Data
public class SoftwareVersion {

    private String versionNumber;
    private String versionName;
    private String versionDescription;
    private String releaseDate;
    private String endOfLifeDate;
    private Boolean isActive;
    private Boolean isLTE;

}
