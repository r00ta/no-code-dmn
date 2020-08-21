package com.r00ta.dmn.messaging.incoming.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutionRequestDTO {
    @JsonProperty("requestId")
    public String requestId;

    @JsonProperty("dmnModel")
    public String dmnModel;

    @JsonProperty("jsonRequest")
    public String jsonRequest;
}
