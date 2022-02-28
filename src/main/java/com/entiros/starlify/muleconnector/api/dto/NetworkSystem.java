package com.entiros.starlify.muleconnector.api.dto;

import lombok.Data;

import java.util.List;
import java.util.Objects;

@Data
public class NetworkSystem extends BaseDto {
    private List<Reference> references;
}
