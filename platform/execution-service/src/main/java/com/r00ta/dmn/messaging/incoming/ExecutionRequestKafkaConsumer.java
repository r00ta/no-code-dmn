package com.r00ta.dmn.messaging.incoming;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.dmn.IExecutionService;
import com.r00ta.dmn.messaging.incoming.dto.CloudEvent;
import com.r00ta.dmn.models.ExecutionRequest;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ExecutionRequestKafkaConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExecutionRequestKafkaConsumer.class);

    @Inject
    IExecutionService executionService;

    @Incoming("enrichment-topic")
    public void onProcessInstanceEvent(CloudEvent event) {
        processEvent(event);
    }

    protected void processEvent(CloudEvent event) {
        LOGGER.info("Processing a new event");
        executionService.processRequest(new ExecutionRequest(event.data.event));
    }
}
