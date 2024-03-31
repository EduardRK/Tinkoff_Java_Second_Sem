package edu.java.scrapper_body.scrapper_body.retry;

import edu.java.configuration.RetryConfig;
import java.time.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

@Slf4j
public final class RetryFactory {
    private final RetryConfig retryConfig;

    public RetryFactory(RetryConfig retryConfig) {
        this.retryConfig = retryConfig;
    }

    public Retry constantRetry() {
        return Retry.fixedDelay(
                retryConfig.maxAttempts(),
                retryConfig.baseDelay()
            )
            .doAfterRetry(retrySignal -> log.error("Constant retry error"))
            .filter(throwable -> isStatusCodeSupportsRetry(retryConfig, throwable));
    }

    public Retry linearRetry() {
        return Retry.from(
            retrySignalFlux -> retrySignalFlux.flatMap(
                retrySignal -> {
                    if (isStatusCodeSupportsRetry(retryConfig, retrySignal.failure())) {
                        return Mono.error(retrySignal.failure());
                    }

                    long currentAttempt = retrySignal.totalRetries() + 1;
                    Duration currentDelay = retryConfig.baseDelay().multipliedBy(currentAttempt);

                    if (currentAttempt < retryConfig.maxAttempts()) {
                        return Mono.delay(currentDelay);
                    } else {
                        return Mono.error(retrySignal.failure());
                    }
                }
            )
        );
    }

    public Retry exponentialRetry() {
        return Retry.backoff(
                retryConfig.maxAttempts(),
                retryConfig.baseDelay()
            ).doAfterRetry(retrySignal -> log.error("Exponential retry error"))
            .filter(throwable -> isStatusCodeSupportsRetry(retryConfig, throwable));
    }

    private boolean isStatusCodeSupportsRetry(RetryConfig retryConfig, Throwable throwable) {
        if (throwable instanceof WebClientResponseException exception) {
            return retryConfig.statusCodes().contains(exception.getStatusCode().value());
        }

        return false;
    }
}
