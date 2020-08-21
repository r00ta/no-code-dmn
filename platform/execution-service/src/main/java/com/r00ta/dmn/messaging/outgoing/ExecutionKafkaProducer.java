package com.r00ta.dmn.messaging.outgoing;

import javax.enterprise.context.ApplicationScoped;

import com.r00ta.dmn.messaging.outgoing.dto.ExecutionDTO;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.reactivestreams.Publisher;

@ApplicationScoped
public class ExecutionKafkaProducer extends AbstractKafkaProducer<ExecutionDTO> {

    @Override
    @Outgoing("execution-result")
    public Publisher<String> getEventPublisher() {
        return super.getEventPublisher();
    }
}
