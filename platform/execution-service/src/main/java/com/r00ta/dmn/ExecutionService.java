package com.r00ta.dmn;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import com.r00ta.dmn.messaging.outgoing.ExecutionKafkaProducer;
import com.r00ta.dmn.messaging.outgoing.dto.ExecutionDTO;
import com.r00ta.dmn.models.ExecutionRequest;
import com.r00ta.dmn.models.StreamGobbler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
public class ExecutionService implements IExecutionService {

    private static Logger LOGGER = LoggerFactory.getLogger(ExecutionService.class);

    private static final String DOCKER_BUILD_IMAGE = "docker build -f %s %s -t test";
    private static final String DOCKER_COMMAND = "docker run -v %s:/kogito-quickstart/src/main/resources/%s -t test:latest";

    @Inject
    ExecutionKafkaProducer executionKafkaProducer;

    public ExecutionService() {
    }

    @PostConstruct
    public void setup() {
        try {
            String content = new String(Files.readAllBytes(Paths.get(getClass().getResource("/executor.jvm").toURI())));
            Path tmp = Files.createTempFile("", "executor.jvm");
            Path tmpDir = Files.createTempDirectory("");
            Files.writeString(tmp, content);
            LOGGER.info(tmp.toAbsolutePath().toString());
            Process process = Runtime.getRuntime()
                    .exec(String.format(DOCKER_BUILD_IMAGE, tmp.toAbsolutePath().toString(), tmpDir.toAbsolutePath().toString()));
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), LOGGER::info);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            int exitCode = process.waitFor();
            LOGGER.info(String.valueOf(exitCode));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void processRequest(ExecutionRequest executionRequest) {
        Path file = createTmpFile(executionRequest.getDmnModel());

        executeRequest(file, executionRequest.getJsonRequest());
        deleteFile(file);

        // TODO: replace with result
        executionKafkaProducer.sendEventAsync(new ExecutionDTO());
    }

    private String executeRequest(Path file, String jsonRequest) {
        try {
            LOGGER.info(String.format(DOCKER_COMMAND, file.toAbsolutePath().toString(), file.getFileName().toString()));
            Process process = Runtime.getRuntime()
                    .exec(String.format(DOCKER_COMMAND, file.toAbsolutePath().toString(), file.getFileName().toString()));
            StreamGobbler streamGobbler =
                    new StreamGobbler(process.getInputStream(), LOGGER::info);
            StreamGobbler streamGobblerError =
                    new StreamGobbler(process.getErrorStream(), LOGGER::info);
            Executors.newSingleThreadExecutor().submit(streamGobbler);
            Executors.newSingleThreadExecutor().submit(streamGobblerError);
            int exitCode = process.waitFor();
            LOGGER.info(String.valueOf(exitCode));
            return "";
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void deleteFile(Path file) {
        try {
            Files.deleteIfExists(file);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private Path createTmpFile(String dmnModel) {
        try {
            Path newFilePath = Files.createTempFile("", ".dmn");
            Files.writeString(newFilePath, dmnModel);
            return newFilePath.getFileName();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
