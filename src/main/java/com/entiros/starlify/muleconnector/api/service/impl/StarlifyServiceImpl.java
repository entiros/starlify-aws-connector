package com.entiros.starlify.muleconnector.api.service.impl;

import com.entiros.starlify.muleconnector.api.dto.*;
import com.entiros.starlify.muleconnector.api.service.AwsService;
import com.entiros.starlify.muleconnector.api.service.StarlifyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StarlifyServiceImpl implements StarlifyService {
    private final RestTemplate restTemplate;

    private final AwsService muleService;

    @Value("${starlify.url}")
    private String starlifyServer;


    @Override
    public List<NetworkSystem> getSystems(Request request) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", request.getStarlifyKey());
        List<NetworkSystem> body = restTemplate.exchange(starlifyServer + "/hypermedia/networks/{networkId}/systems?paged=false",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<List<NetworkSystem>>() {
                }, request.getNetworkId()).getBody();
        return body;
    }

    @Override
    public SystemRespDto addSystem(Request request, SystemDto systemDto) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", request.getStarlifyKey());
        SystemRespDto body = restTemplate.exchange(starlifyServer + "/hypermedia/networks/{networkId}/systems",
                HttpMethod.POST,
                new HttpEntity<>(systemDto, headers),
                new ParameterizedTypeReference<SystemRespDto>() {
                }, request.getNetworkId()).getBody();
        return body;
    }

    @Override
    public String addService(Request request, ServiceDto serviceDto, String systemId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", request.getStarlifyKey());
        String body = restTemplate.exchange(starlifyServer + "/hypermedia/systems/{systemId}/services",
                HttpMethod.POST,
                new HttpEntity<>(serviceDto, headers),
                new ParameterizedTypeReference<String>() {
                }, systemId).getBody();
        return body;
    }

    @Override
    public Response<ServiceRespDto> getServices(Request request, String systemId) {
        HttpHeaders headers = new HttpHeaders();
        headers.add("X-API-KEY", request.getStarlifyKey());
        Response<ServiceRespDto> body = restTemplate.exchange(starlifyServer + "/hypermedia/systems/{systemId}/services",
                HttpMethod.GET,
                new HttpEntity<>(null, headers),
                new ParameterizedTypeReference<Response<ServiceRespDto>>() {
                }, systemId).getBody();
        return body;
    }


}
