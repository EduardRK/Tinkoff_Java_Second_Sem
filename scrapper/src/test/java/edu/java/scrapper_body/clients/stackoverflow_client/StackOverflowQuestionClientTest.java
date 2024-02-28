package edu.java.scrapper_body.clients.stackoverflow_client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import edu.java.scrapper_body.clients_body.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class StackOverflowQuestionClientTest {
    private static final WireMockServer WIRE_MOCK_SERVER = new WireMockServer();
    private static final String ANSWER_LINK =
        "https://stackoverflow.com/questions/78040876/maven-generate-sources-generates-classes-with-javax-instead-of-jakarta-namespace";

    @BeforeAll
    public static void serverStart() {
        WIRE_MOCK_SERVER
            .stubFor(
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
    void newUpdates() throws URISyntaxException, JsonProcessingException {
        StackOverflowQuestionClient client = new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl());
        List<Response> responses = new ArrayList<>(
            List.of(
                new Response(
                    new URI(ANSWER_LINK),
                    "gbuys",
                    "<p>It seems like you should use jaxb-maven-plugin instead of maven-jaxb2-plugin. See <a href=\"https://github.com/highsource/jaxb-tools?tab=readme-ov-file#jaxb-maven-plugin\" rel=\"nofollow noreferrer\">github</a></p>\n",
                    OffsetDateTime.ofInstant(
                        Instant.ofEpochSecond(
                            1708607611
                        ),
                        ZoneOffset.UTC
                    )
                )
            )
        );

        Assertions.assertEquals(
            responses,
            client.newUpdates(new URI(ANSWER_LINK))
        );
    }

    @Test
    void notValid() throws URISyntaxException {
        StackOverflowQuestionClient client = new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl());

        Assertions.assertTrue(
            client.notValid(new URI(
                "https://github.com/gunnarmorling/1brc"))
        );
    }

    @Test
    void constructorsTest() {
        Assertions.assertDoesNotThrow(
            () -> {
                new StackOverflowQuestionClient(WIRE_MOCK_SERVER.baseUrl());
                new StackOverflowQuestionClient();
            }
        );
    }
}
