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
import java.time.Duration;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;

class ClientChainTest {
    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer(8070);
    private static final String REPOSITORY_LINK = "https://github.com/EduardRK/Fractal-Flame";
    Retry retry = Retry.fixedDelay(1, Duration.ofSeconds(10));

    @BeforeAll
    public static void serverStart() throws IOException {
        WIRE_MOCK_SERVER
            .stubFor(
                WireMock
                    .get(
                        WireMock.urlEqualTo(
                            "/repos/EduardRK/Fractal-Flame/commits"
                        )
                    )
                    .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(
                            new FileContent("src/main/resources/github-test-data.json").content()
                        )
                    )
            );
        WIRE_MOCK_SERVER.stubFor(
            WireMock
                .get(
                    WireMock.urlEqualTo(
                        "/questions/78040876/answers?order=desc&sort=votes&site=stackoverflow&filter=!nNPvSNdWme"
                    )
                )
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
            ClientChain.defaultChain(retry);
        });
    }

    @Test
    void newUpdates() throws URISyntaxException, JsonProcessingException {
        GitHubCommitUpdateClient gitHubClient = new GitHubCommitUpdateClient(WIRE_MOCK_SERVER.baseUrl(), retry);
        StackOverflowQuestionClient stackOverflowClient =
            new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl(), retry, gitHubClient);

        ClientChain clientChain = new ClientChain(stackOverflowClient);

        List<? extends Response> responses = new ArrayList<>(
            List.of(
                new Response() {
                    @Override
                    public String author() {
                        return "EduardRK";
                    }

                    @Override
                    public String message() {
                        return "Fractal flame";
                    }

                    @Override
                    public OffsetDateTime date() {
                        return OffsetDateTime.parse("2024-01-11T18:51:01Z");
                    }
                }
            )
        );

        Assertions.assertEquals(
            responses.getFirst().author(),
            clientChain.newUpdates(new URI(REPOSITORY_LINK)).getFirst().author()
        );
        Assertions.assertEquals(
            responses.getFirst().message(),
            clientChain.newUpdates(new URI(REPOSITORY_LINK)).getFirst().message()
        );
        Assertions.assertEquals(
            responses.getFirst().date(),
            clientChain.newUpdates(new URI(REPOSITORY_LINK)).getFirst().date()
        );
    }
}
