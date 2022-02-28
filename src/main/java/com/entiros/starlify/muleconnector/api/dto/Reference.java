package com.entiros.starlify.muleconnector.api.dto;

import lombok.Data;

@Data
public class Reference extends BaseDto {
    private NetworkSystem consumingNetworkSystem;
    private Service service;
}
