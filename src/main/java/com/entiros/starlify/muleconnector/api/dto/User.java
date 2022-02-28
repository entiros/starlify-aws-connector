package com.entiros.starlify.muleconnector.api.dto;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class User{
    public String id;
    public Date createdAt;
    public Date updatedAt;
    public String organizationId;
    public String firstName;
    public String lastName;
    public String email;
    public String phoneNumber;
    public String username;
    public String idprovider_id;
    public boolean enabled;
    public boolean deleted;
    public Date lastLogin;
    public boolean mfaVerificationExcluded;
    public String mfaVerifiersConfigured;
    public boolean isFederated;
    public String type;
    public OrganizationPreferences organizationPreferences;
    public Organization organization;
    public Properties properties;
    public List<MemberOfOrganization> memberOfOrganizations;
    public List<ContributorOfOrganization> contributorOfOrganizations;
}
