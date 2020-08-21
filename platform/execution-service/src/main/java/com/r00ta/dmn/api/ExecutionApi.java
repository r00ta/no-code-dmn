package com.r00ta.dmn.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.r00ta.dmn.IExecutionService;
import com.r00ta.dmn.models.ExecutionRequest;

@Path("/execution")
public class ExecutionApi {

    @Inject
    IExecutionService executionService;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response hello(ExecutionRequest executionRequest) {
        executionService.processRequest(executionRequest);
        return Response.ok().build();
    }
}