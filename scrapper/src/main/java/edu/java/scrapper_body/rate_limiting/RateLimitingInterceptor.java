package edu.java.scrapper_body.rate_limiting;

import edu.java.configuration.RateLimitingConfig;
import edu.java.exceptions.TooManyRequestsException;
import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public final class RateLimitingInterceptor implements HandlerInterceptor {
    private final RateLimitingConfig rateLimitingConfig;
    private final Map<String, Bucket> cache = new ConcurrentHashMap<>();

    public RateLimitingInterceptor(RateLimitingConfig rateLimitingConfig) {
        this.rateLimitingConfig = rateLimitingConfig;
    }

    @Override
    public boolean preHandle(
        @NotNull HttpServletRequest request,
        @NotNull HttpServletResponse response,
        @NotNull Object handler
    ) throws TooManyRequestsException {
        String ip = request.getRemoteAddr();

        Bucket token = cache.computeIfAbsent(ip, string -> bucket());

        if (token.tryConsume(rateLimitingConfig.refillTokens())) {
            return true;
        } else {
            throw new TooManyRequestsException("Too many requests");
        }

    }

    Bucket bucket() {
        return Bucket
            .builder()
            .addLimit(
                Bandwidth.classic(
                    rateLimitingConfig.capacity(),
                    Refill.greedy(
                        rateLimitingConfig.refillTokens(),
                        rateLimitingConfig.refillInterval()
                    )
                )
            )
            .build();
    }
}
