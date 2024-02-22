package edu.java.scrapper_body.clients.github_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper_body.clients.stackoverflow_client.StackOverflowQuestionClient;
import edu.java.scrapper_body.clients_body.Response;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.net.URISyntaxException;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

class GitHubCommitUpdateClientTest {
    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer();
    private static final String REPOSITORY_LINK = "https://github.com/EduardRK/Fractal-Flame";

    @BeforeAll
    public static void serverStart() {
        WIRE_MOCK_SERVER
                .stubFor(
                        WireMock.get(WireMock.urlEqualTo("/repos/EduardRK/Fractal-Flame/commits"))
                                .willReturn(WireMock.aResponse()
                                        .withStatus(200)
                                        .withHeader("Content-Type", "application/json")
                                        .withBody(
                                                """
                                                        [
                                                            {
                                                                "sha": "30084aa6abd951f6bf9a730e882fb63cbe5b05dd",
                                                                "node_id": "C_kwDOLDsvgNoAKDMwMDg0YWE2YWJkOTUxZjZiZjlhNzMwZTg4MmZiNjNjYmU1YjA1ZGQ",
                                                                "commit": {
                                                                    "author": {
                                                                        "name": "EduardRK",
                                                                        "date": "2024-01-11T18:51:01Z"
                                                                    },
                                                                    "committer": {
                                                                        "name": "EduardRK",
                                                                        "date": "2024-01-11T18:51:01Z"
                                                                    },
                                                                    "message": "Fractal flame",
                                                                    "tree": {
                                                                        "sha": "219e04b962d9111bc113f580dc3284a349a8c7fe",
                                                                        "url": "https://api.github.com/repos/EduardRK/Fractal-Flame/git/trees/219e04b962d9111bc113f580dc3284a349a8c7fe"
                                                                    },
                                                                    "url": "https://api.github.com/repos/EduardRK/Fractal-Flame/git/commits/30084aa6abd951f6bf9a730e882fb63cbe5b05dd",
                                                                    "comment_count": 0,
                                                                    "verification": {
                                                                        "verified": false,
                                                                        "reason": "unsigned",
                                                                        "signature": null,
                                                                        "payload": null
                                                                    }
                                                                }
                                                            }
                                                        ]
                                                        """
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
        GitHubCommitUpdateClient client = new GitHubCommitUpdateClient(WIRE_MOCK_SERVER.baseUrl());
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
                client.newUpdates(new URI(REPOSITORY_LINK))
        );
    }

    @Test
    void notValid() throws URISyntaxException {
        GitHubCommitUpdateClient gitHubCommitUpdateClient = new GitHubCommitUpdateClient();

        Assertions.assertTrue(
                gitHubCommitUpdateClient.notValid(new URI(
                        "https://stackoverflow.com/questions/78041785/spring-boot-maven-package"))
        );
    }

    @Test
    void constructorsTest() {
        Assertions.assertDoesNotThrow(
                () -> {
                    new GitHubCommitUpdateClient("other.api.github.com");
                    new GitHubCommitUpdateClient(
                            new StackOverflowQuestionClient()
                    );
                }
        );
    }
}
