package com.entiros.starlify.muleconnector.api.service.impl;

import com.entiros.starlify.muleconnector.api.service.AwsService;
import com.entiros.starlify.muleconnector.aws.AWSV4Auth;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.json.simple.JsonArray;
import org.apache.camel.json.simple.JsonObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;

@Service
@Slf4j
@RequiredArgsConstructor
public class AwsServiceImpl implements AwsService {

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    @Value("${aws.server.url}")
    private String apiServer;


    @Override
    public JsonArray getApis(String apiKey, String apiSecret, String region) {

        String url = apiServer.replace("https://", "");


        TreeMap<String, String> awsHeaders = new TreeMap<String, String>();
        awsHeaders.put("host", url);

        AWSV4Auth aWSV4Auth = new AWSV4Auth.Builder(apiKey, apiSecret)
                .regionName(region)
                .serviceName("apigateway")
                .httpMethodName("GET")
                .canonicalURI("/restapis")
                .queryParametes(null)
                .awsHeaders(awsHeaders)
                .payload(null)
                .debug()
                .build();


        HttpHeaders headers = new HttpHeaders();

        Map<String, String> header = aWSV4Auth.getHeaders();
        for (Map.Entry<String, String> entrySet : header.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            headers.add(key, value);
        }

        ResponseEntity<JsonObject> response = restTemplate.exchange(apiServer + "/restapis",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<JsonObject>() {
                });
        log.info(response.getBody().toJson());
        JsonArray apiArray = new JsonArray();

        JsonArray temp = objectMapper.convertValue(response.getBody().get("item"), JsonArray.class);

        for (Object obj : temp) {
            if (apiArray.size() > 4) {
                continue;
            }
            JsonObject obj2 = objectMapper.convertValue(obj, JsonObject.class);
            JsonObject api = new JsonObject();
            api.put("id", obj2.getString("id"));
            api.put("name", obj2.getString("name"));
            apiArray.add(getApiResources(apiKey, apiSecret, region, api));
        }
        return apiArray;

    }

    @Override
    public JsonObject getApiResources(String apiKey, String apiSecret, String region, JsonObject api) {
        String url = apiServer.replace("https://", "");


        TreeMap<String, String> awsHeaders = new TreeMap<String, String>();
        awsHeaders.put("host", url);

        AWSV4Auth aWSV4Auth = new AWSV4Auth.Builder(apiKey, apiSecret)
                .regionName(region)
                .serviceName("apigateway")
                .httpMethodName("GET")
                .canonicalURI("/restapis/" + api.getString("id") + "/resources")
                .queryParametes(null)
                .awsHeaders(awsHeaders)
                .payload(null)
                .debug()
                .build();


        HttpHeaders headers = new HttpHeaders();

        Map<String, String> header = aWSV4Auth.getHeaders();
        for (Map.Entry<String, String> entrySet : header.entrySet()) {
            String key = entrySet.getKey();
            String value = entrySet.getValue();
            headers.add(key, value);
        }

        ResponseEntity<JsonObject> response = restTemplate.exchange(apiServer + "/restapis/" + api.getString("id") + "/resources",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<JsonObject>() {
                });
        log.info(response.getBody().toJson());

        JsonArray resources = new JsonArray();
        JsonArray item = getJsonArray(response.getBody(), "item");

        for (Object obj : item) {
            JsonObject jsonObject = objectMapper.convertValue(obj, JsonObject.class);
            String path = jsonObject.getString("path");
            if (!jsonObject.containsKey("resourceMethods")) {
                resources.add(path);
                continue;
            }
            JsonObject resourceArray = getJsonObject(jsonObject, "resourceMethods");
            Map<String, Object> resMap = objectMapper.convertValue(resourceArray, Map.class);
            for (String method : resMap.keySet()) {
                resources.add(path + " " + method);
            }
        }
        api.put("resources", resources);
        return api;
    }

    private JsonArray getJsonArray(JsonObject jsonObject, String key) {
        return objectMapper.convertValue(jsonObject.get(key), JsonArray.class);
    }

    private JsonObject getJsonObject(JsonObject jsonObject, String key) {
        return objectMapper.convertValue(jsonObject.get(key), JsonObject.class);
    }

}
