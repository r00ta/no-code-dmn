package com.r00ta.dmn.messaging.outgoing.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ExecutionDTO implements CloudEventDTO {
    @JsonProperty("requestId")
    public String requestId;

    @JsonProperty("response")
    public String response;

    @Override
    public String getEventId() {
        return null;
    }

    @Override
    public String getEventProducer() {
        return null;
    }
}
