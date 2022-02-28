package com.entiros.starlify.muleconnector.api.service;

import com.entiros.starlify.muleconnector.api.dto.Request;
import com.entiros.starlify.muleconnector.api.dto.RequestItem;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.concurrent.ExecutionException;

public interface StarlifyExportService {
    public RequestItem submitRequest(Request request) throws ExecutionException, InterruptedException;
    public RequestItem status(Request request);


}
