package com.entiros.starlify.muleconnector.api.service.impl;

import com.entiros.starlify.muleconnector.api.dto.*;
import com.entiros.starlify.muleconnector.api.service.AwsService;
import com.entiros.starlify.muleconnector.api.service.StarlifyExportService;
import com.entiros.starlify.muleconnector.api.service.StarlifyService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.json.simple.JsonArray;
import org.apache.camel.json.simple.JsonObject;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;

@Slf4j
@Service
@RequiredArgsConstructor
public class StarlifyExportServiceImpl implements StarlifyExportService {

    private final AwsService awsService;
    private final StarlifyService starlifyService;
    private final ObjectMapper objectMapper;


    private Map<String, Map<String, NetworkSystem>> cachedNetworkSystems = new ConcurrentHashMap<>();
    private Map<String, RequestItem> statusMap = new ConcurrentHashMap<>();

    private void processRequest(Request request) {
        ((RequestItem) request).setStatus(RequestItem.Status.IN_PROCESS);
        List<NetworkSystem> systems = starlifyService.getSystems(request);
        JsonArray apis = awsService.getApis(request.getApiKey(), request.getApiSecret(), request.getRegion());
        this.populateSystems(request, systems);

        Map<String, NetworkSystem> existingSystems = cachedNetworkSystems.get(request.getNetworkId());
        for (Object obj : apis) {
            JsonObject api = objectMapper.convertValue(obj, JsonObject.class);
            try {
                log.info("Started asset:" + api.getString("name"));
                NetworkSystem networkSystem = existingSystems != null ? existingSystems.get(api.getString("name")) : null;
                String systemId = null;
                if (networkSystem == null) {
                    SystemDto systemDto = this.createSystemDto(request, api.getString("name"), api.getString("name"));
                    SystemRespDto systemRespDto = starlifyService.addSystem(request, systemDto);
                    systemId = systemRespDto.getId();
                } else {
                    systemId = networkSystem.getId();
                }

                Response<ServiceRespDto> services = starlifyService.getServices(request, systemId);
                Set<String> serviceNames = this.getServiceNames(services);

                JsonArray resources = objectMapper.convertValue(api.get("resources"), JsonArray.class);
                for (Object obj2 : resources) {
                    String resource = objectMapper.convertValue(obj2, String.class);
                    if (!serviceNames.contains(resource)) {
                        ServiceDto dto = new ServiceDto();
                        dto.setName(resource);
                        starlifyService.addService(request, dto, systemId);
                    }
                }
                ((RequestItem) request).setStatus(RequestItem.Status.DONE);

            } catch (Throwable t) {
                log.error("Error while processing asset:" + api.getString("name"), t);
                ((RequestItem) request).setStatus(RequestItem.Status.ERROR);
            }
        }
    }

    private synchronized Set<String> getServiceNames(Response<ServiceRespDto> services) {
        List<ServiceRespDto> content = services.getContent();
        Set<String> ret = new HashSet<>();
        if (content != null && !content.isEmpty()) {
            for (ServiceRespDto c : content) {
                ret.add(c.getName());
            }
        }
        return ret;
    }


    @Override
    public RequestItem submitRequest(Request request) throws ExecutionException, InterruptedException {
        RequestItem workItem = new RequestItem();
        workItem.setStatus(RequestItem.Status.NOT_STARTED);
        workItem.setStarlifyKey(request.getStarlifyKey());
        workItem.setApiKey(request.getApiKey());
        workItem.setNetworkId(request.getNetworkId());
        workItem.setApiSecret(request.getApiSecret());
        workItem.setRegion(request.getRegion());
        statusMap.put(request.getNetworkId(), workItem);
        CompletableFuture.runAsync(() -> {
            processRequest(workItem);
        });
        return workItem;
    }


    @Override
    public RequestItem status(Request request) {
        return statusMap.get(request.getNetworkId());
    }


    private SystemDto createSystemDto(Request request, String name, String description) {
        SystemDto s = new SystemDto();
        String id = UUID.randomUUID().toString();
        s.setId(id);
        s.setName(name);
        Network n = new Network();
        n.setId(request.getNetworkId());
        s.setNetwork(n);
        s.setDescription(description);
        return s;
    }

    private synchronized void populateSystems(Request request, List<NetworkSystem> networkSystems) {
        if (networkSystems != null && !networkSystems.isEmpty()) {
            Map<String, NetworkSystem> existingSystems = cachedNetworkSystems.get(request.getNetworkId());
            if (existingSystems == null) {
                existingSystems = new ConcurrentHashMap<>();
                cachedNetworkSystems.put(request.getNetworkId(), existingSystems);
            }
            for (NetworkSystem ns : networkSystems) {
                existingSystems.put(ns.getName(), ns);
            }
        }
    }


}
