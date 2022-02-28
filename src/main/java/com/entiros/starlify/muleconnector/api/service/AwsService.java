package com.entiros.starlify.muleconnector.api.service;

import com.entiros.starlify.muleconnector.api.dto.Asset;
import com.entiros.starlify.muleconnector.api.dto.AssetDetails;
import com.entiros.starlify.muleconnector.api.dto.UserProfile;
import org.apache.camel.json.simple.JsonArray;
import org.apache.camel.json.simple.JsonObject;

import java.util.List;

public interface AwsService {
    JsonObject getApiResources(String apiKey, String apiSecret, String region, JsonObject api);


    JsonArray getApis(String apiKey, String apiSecret, String region);
}
