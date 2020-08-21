package com.r00ta.dmn.messaging.incoming;

import com.r00ta.dmn.messaging.outgoing.dto.ExecutionDTO;

public class ExecutionRequestDeserializer extends AbstractCloudEventDeserializer<ExecutionDTO> {

    public ExecutionRequestDeserializer() {
        super(ExecutionDTO.class);
    }
}