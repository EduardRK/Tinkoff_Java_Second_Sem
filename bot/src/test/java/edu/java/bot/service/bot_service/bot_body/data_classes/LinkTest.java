package edu.java.bot.service.bot_service.bot_body.data_classes;

import java.net.URI;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LinkTest {
    @ParameterizedTest
    @CsvSource(value = {
        "https://github.com/, false",
        "Some wrong link, true"
    })
    void testConstructors(String string, boolean exceptionExpected) throws URISyntaxException {
        if (exceptionExpected) {
            Assertions.assertThrows(URISyntaxException.class, () -> new Link(string));
        } else {
            Assertions.assertDoesNotThrow(() -> new Link(string));

            Link link = new Link(string);
            Link link1 = new Link(new URI(string));
            Link link2 = new Link(link);

            Assertions.assertEquals(link, link1);
            Assertions.assertEquals(link1, link2);
            Assertions.assertEquals(link2, link);
        }
    }

    @ParameterizedTest
    @CsvSource(value = {
        "https://github.com/"
    })
    void testToString(String string) throws URISyntaxException {
        Link link = new Link(string);

        Assertions.assertEquals(
            string,
            link.toString()
        );
    }

    @ParameterizedTest
    @CsvSource(value = {
        "https://github.com/"
    })
    void testUri(String string) throws URISyntaxException {
        Link link = new Link(string);

        Assertions.assertEquals(
            new URI(string),
            link.uri()
        );
    }
}
