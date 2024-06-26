package edu.java.scrapper_body.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper_body.scrapper_body.clients.support.FileContent;
import edu.java.scrapper_body.scrapper_body.clients_body.Response;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Duration;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import reactor.util.retry.Retry;

class StackOverflowQuestionClientTest {
    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer(8070);
    private static final String ANSWER_LINK =
        "https://stackoverflow.com/questions/78040876/maven-generate-sources-generates-classes-with-javax-instead-of-jakarta-namespace";
    Retry retry = Retry.fixedDelay(1, Duration.ofSeconds(10));

    @BeforeAll
    public static void serverStart() throws IOException {
        WIRE_MOCK_SERVER
            .stubFor(
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
    void newUpdates() throws URISyntaxException, JsonProcessingException {
        StackOverflowQuestionClient client = new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl(), retry);
        List<Response> responses = new ArrayList<>(
            List.of(
                new Response() {
                    @Override
                    public String author() {
                        return "gbuys";
                    }

                    @Override
                    public String message() {
                        return "<p>It seems like you should use jaxb-maven-plugin instead of maven-jaxb2-plugin. See <a href=\"https://github.com/highsource/jaxb-tools?tab=readme-ov-file#jaxb-maven-plugin\" rel=\"nofollow noreferrer\">github</a></p>\n";
                    }

                    @Override
                    public OffsetDateTime date() {
                        return OffsetDateTime.ofInstant(
                            Instant.ofEpochSecond(
                                1708607611
                            ),
                            ZoneOffset.UTC
                        );
                    }
                }
            )
        );

        Assertions.assertEquals(
            responses.getFirst().date(),
            client.newUpdates(new URI(ANSWER_LINK)).getFirst().date()
        );
        Assertions.assertEquals(
            responses.getFirst().author(),
            client.newUpdates(new URI(ANSWER_LINK)).getFirst().author()
        );
        Assertions.assertEquals(
            responses.getFirst().message(),
            client.newUpdates(new URI(ANSWER_LINK)).getFirst().message()
        );
    }

    @Test
    void notValid() throws URISyntaxException {
        StackOverflowQuestionClient client = new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl(), retry);

        Assertions.assertTrue(
            client.notValid(new URI(
                "https://github.com/gunnarmorling/1brc"))
        );
    }

    @Test
    void constructorsTest() {
        Assertions.assertDoesNotThrow(
            () -> {
                new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl(), retry);
                new StackOverflowQuestionClient(retry);
            }
        );
    }
}
