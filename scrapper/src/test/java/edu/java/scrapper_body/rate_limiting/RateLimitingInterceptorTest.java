package edu.java.scrapper_body.rate_limiting;

import edu.java.configuration.RateLimitingConfig;
import edu.java.exceptions.TooManyRequestsException;
import edu.java.scrapper.IntegrationTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@SpringBootTest
class RateLimitingInterceptorTest extends IntegrationTest {
    @Autowired
    RateLimitingInterceptor interceptor;

    @Autowired
    RateLimitingConfig rateLimitingConfig;

    @DynamicPropertySource
    static void jdbcProperties(DynamicPropertyRegistry registry) {
        registry.add("rate-limiting.capacity", () -> 1);
        registry.add("rate-limiting.refill-tokens", () -> 1);
        registry.add("rate-limiting.refill-interval", () -> "1m");
    }

    @Test
    @DirtiesContext
    void testPreHandle_SuccessfulRequest() throws TooManyRequestsException {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setRemoteAddr("127.0.0.1");

        MockHttpServletResponse response = new MockHttpServletResponse();

        Assertions.assertDoesNotThrow(
            () -> interceptor.preHandle(request, response, new Object())
        );
    }

    @Test
    @DirtiesContext
    void testPreHandle_TooManyRequestsException() {
        MockHttpServletRequest request1 = new MockHttpServletRequest();
        request1.setRemoteAddr("127.0.0.1");

        MockHttpServletRequest request2 = new MockHttpServletRequest();
        request2.setRemoteAddr("127.0.0.1");

        MockHttpServletResponse response = new MockHttpServletResponse();

        Assertions.assertDoesNotThrow(
            () -> interceptor.preHandle(request1, response, new Object())
        );

        Assertions.assertThrows(
            TooManyRequestsException.class, () -> interceptor.preHandle(request2, response, new Object())
        );
    }
}
