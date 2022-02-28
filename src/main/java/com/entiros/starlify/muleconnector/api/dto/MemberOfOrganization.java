package com.entiros.starlify.muleconnector.api.dto;
import java.util.Date;
import java.util.List;
public class MemberOfOrganization{
    public Object parentName;
    public Object parentId;
    public String domain;
    public String name;
    public String id;
    public Date createdAt;
    public Date updatedAt;
    public String ownerId;
    public String clientId;
    public String idprovider_id;
    public boolean isFederated;
    public List<Object> parentOrganizationIds;
    public List<Object> subOrganizationIds;
    public List<Object> tenantOrganizationIds;
    public String mfaRequired;
    public boolean isAutomaticAdminPromotionExempt;
    public String orgType;
    public boolean isMaster;
    public Subscription subscription;
}
