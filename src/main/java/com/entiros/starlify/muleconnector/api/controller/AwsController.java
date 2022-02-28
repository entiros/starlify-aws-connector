package com.entiros.starlify.muleconnector.api.controller;

import com.entiros.starlify.muleconnector.api.dto.*;
import com.entiros.starlify.muleconnector.api.service.StarlifyExportService;
import com.entiros.starlify.muleconnector.aws.AWSV4Auth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.json.simple.JsonObject;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.ExecutionException;

@Slf4j
@RestController
@RequestMapping
@RequiredArgsConstructor
public class AwsController {

    private final StarlifyExportService starlifyExportService;

    private final RestTemplate restTemplate;

    @PostMapping("/status")
    public RequestItem getStatus(@RequestBody Request request) {
        return starlifyExportService.status(request);
    }

    @PostMapping("/submitRequest")
    public RequestItem processRequest(@RequestBody Request request) throws ExecutionException, InterruptedException {
        return starlifyExportService.submitRequest(request);
    }



}

