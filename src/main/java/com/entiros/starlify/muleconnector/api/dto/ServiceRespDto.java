package com.entiros.starlify.muleconnector.api.dto;

import lombok.Data;

@Data
public class ServiceRespDto extends BaseDto {
    private String localId;
    private String provider;
    private String invocations;
    private String certifiedIntegratorCompliant;
    private String attributes;
    private String engagements;
    private String observations;
    private String releaseStage;
    private String releases;
    private String domain;
    private String network;
    private String incomingReferences;
}
