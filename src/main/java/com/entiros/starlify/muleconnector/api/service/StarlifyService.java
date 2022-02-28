package com.entiros.starlify.muleconnector.api.service;

import com.entiros.starlify.muleconnector.api.dto.*;

import java.util.List;

public interface StarlifyService {
    public List<NetworkSystem> getSystems(Request request);

    public SystemRespDto addSystem(Request request, SystemDto systemDto);

    public String addService(Request request, ServiceDto serviceDto, String systemId);

    public Response<ServiceRespDto> getServices(Request request, String systemId);
}
