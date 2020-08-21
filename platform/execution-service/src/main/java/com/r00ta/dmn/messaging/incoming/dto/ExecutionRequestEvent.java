package com.r00ta.dmn.messaging.incoming.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ExecutionRequestEvent {

    @JsonProperty("event")
    public ExecutionRequestDTO event;
}
