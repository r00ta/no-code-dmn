package com.r00ta.dmn.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.r00ta.dmn.messaging.incoming.dto.ExecutionRequestDTO;

public class ExecutionRequest {
    @JsonProperty("requestId")
    public String requestId;

    @JsonProperty("dmnModel")
    public String dmnModel;

    @JsonProperty("jsonRequest")
    public String jsonRequest;

    public ExecutionRequest(){}

    public ExecutionRequest(ExecutionRequestDTO executionRequestDTO){
        this.requestId = executionRequestDTO.requestId;
        this.dmnModel = executionRequestDTO.dmnModel;
        this.jsonRequest = executionRequestDTO.jsonRequest;
    }

    public String getRequestId(){
        return requestId;
    }

    public String getDmnModel(){
        return dmnModel;
    }

    public String getJsonRequest(){
        return jsonRequest;
    }
}
