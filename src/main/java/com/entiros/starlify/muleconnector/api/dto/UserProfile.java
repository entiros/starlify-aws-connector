package com.entiros.starlify.muleconnector.api.dto;


import lombok.Data;

import java.util.List;

@Data
public class UserProfile {
    public User user;
    public List<Asset> assetList;
}
