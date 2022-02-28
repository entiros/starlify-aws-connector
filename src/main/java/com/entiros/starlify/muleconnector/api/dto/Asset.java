package com.entiros.starlify.muleconnector.api.dto;

import lombok.Data;

import java.util.Date;

@Data
public class Asset {
    public String organizationId;
    public String groupId;
    public String assetId;
    public String version;
    public String minorVersion;
    public String versionGroup;
    public String description;
    public boolean isPublic;
    public String name;
    public Object contactName;
    public Object contactEmail;
    public String type;
    public String status;
    public Date createdAt;
    public String createdById;
    public AssetDetails assetDetails;
}