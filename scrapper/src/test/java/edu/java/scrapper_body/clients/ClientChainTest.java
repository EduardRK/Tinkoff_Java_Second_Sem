package edu.java.scrapper_body.clients;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper_body.clients.github_client.GitHubCommitUpdateClient;
import edu.java.scrapper_body.clients.stackoverflow_client.StackOverflowQuestionClient;
import edu.java.scrapper_body.clients_body.Response;
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
        WIRE_MOCK_SERVER.stubFor(
            WireMock.get(WireMock.urlEqualTo(
                    "/questions/78040876/answers?order=desc&sort=votes&site=stackoverflow&filter=!nNPvSNdWme"))
                .willReturn(WireMock.aResponse()
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")
                    .withBody(
                        """
                            {
                                "items": [
                                    {
                                        "owner": {
                                            "account_id": 881430,
                                            "reputation": 360,
                                            "user_id": 263597,
                                            "user_type": "registered",
                                            "profile_image": "https://www.gravatar.com/avatar/d4d90f857b84617ffff47212595dcfe9?s=256&d=identicon&r=PG",
                                            "display_name": "gbuys",
                                            "link": "https://stackoverflow.com/users/263597/gbuys"
                                        },
                                        "is_accepted": true,
                                        "score": 2,
                                        "last_activity_date": 1708607611,
                                        "creation_date": 1708607611,
                                        "answer_id": 78041218,
                                        "question_id": 78040876,
                                        "content_license": "CC BY-SA 4.0",
                                        "body": "<p>It seems like you should use jaxb-maven-plugin instead of maven-jaxb2-plugin. See <a href=\\"https://github.com/highsource/jaxb-tools?tab=readme-ov-file#jaxb-maven-plugin\\" rel=\\"nofollow noreferrer\\">github</a></p>\\n"
                                    }
                                ],
                                "has_more": false,
                                "quota_max": 300,
                                "quota_remaining": 299
                            }
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
