package com.entiros.starlify.muleconnector.api.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class AssetDetails {
    public String groupId;
    public String assetId;
    public String version;
    public String minorVersion;
    public String organizationId;
    public String description;
    public String versionGroup;
    public boolean isPublic;
    public String name;
    public String type;
    public String status;
    public Object contactEmail;
    public Object contactName;
    public List<Object> labels;
    public List<Attribute> attributes;
    public List<Category> categories;
    public List<Object> customFields;
    public List<File> files;
    public List<Dependency> dependencies;
    public Date createdAt;
    public String createdById;
    public List<String> versions;
}