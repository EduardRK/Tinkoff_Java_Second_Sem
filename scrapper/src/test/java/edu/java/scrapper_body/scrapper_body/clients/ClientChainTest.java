package edu.java.scrapper_body.scrapper_body.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper_body.scrapper_body.clients.github_client.GitHubCommitUpdateClient;
import edu.java.scrapper_body.scrapper_body.clients.stackoverflow_client.StackOverflowQuestionClient;
import edu.java.scrapper_body.scrapper_body.clients.support.FileContent;
import edu.java.scrapper_body.scrapper_body.clients_body.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ClientChainTest {
    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer();
    private static final String REPOSITORY_LINK = "https://github.com/EduardRK/Fractal-Flame";

    @BeforeAll
    public static void serverStart() throws IOException {
        WIRE_MOCK_SERVER
            .stubFor(
                WireMock.get(WireMock.urlEqualTo("/repos/EduardRK/Fractal-Flame/commits"))
                    .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            new FileContent("src/main/resources/github-test-data.json").content()
                        )
                    )
            );
        WIRE_MOCK_SERVER.stubFor(
            WireMock.get(WireMock.urlEqualTo(
                    "/questions/78040876/answers?order=desc&sort=votes&site=stackoverflow&filter=!nNPvSNdWme"))
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        new FileContent("src/main/resources/stackoverflow-test-data.json").content()
                    )
                )
        );
        WIRE_MOCK_SERVER.start();
    }

    @AfterAll
    public static void serverStop() {
        WIRE_MOCK_SERVER.stop();
    }

    @Test
    void defaultChain() {
        Assertions.assertDoesNotThrow(() -> {
            ClientChain.defaultChain();
        });
    }

    @Test
    void newUpdates() throws URISyntaxException, JsonProcessingException {
        GitHubCommitUpdateClient gitHubClient = new GitHubCommitUpdateClient(WIRE_MOCK_SERVER.baseUrl());
        StackOverflowQuestionClient stackOverflowClient =
            new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl(), gitHubClient);

        ClientChain clientChain = new ClientChain(stackOverflowClient);

        List<Response> responses = new ArrayList<>(
            List.of(
                new Response(
                    new URI(REPOSITORY_LINK),
                    "EduardRK",
                    "Fractal flame",
                    OffsetDateTime.parse("2024-01-11T18:51:01Z")
                )
            )
        );

        Assertions.assertEquals(
            responses,
            clientChain.newUpdates(new URI(REPOSITORY_LINK))
        );
    }
}
