package co.copper.test.services;

import java.util.concurrent.CompletableFuture;

import org.asynchttpclient.AsyncHttpClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import co.copper.test.storage.TestJavaRepository;


@Service
public class TestJavaService {

    private static final Logger log = LoggerFactory.getLogger(TestJavaService.class);
    private final TestJavaRepository testRepo;
    private final AsyncHttpClient httpClient;

    @Autowired
    public TestJavaService(TestJavaRepository testRepo, AsyncHttpClient httpClient) {
        this.testRepo = testRepo;
        this.httpClient = httpClient;
    }

    public CompletableFuture<String> getOk() {
        log.debug(testRepo.getById(1L).get(0).getVal());
        return this.httpClient.prepareGet("https://postman-echo.com/get").execute().toCompletableFuture()
            .handle((res, t) -> res.getResponseBody());
    }
}
