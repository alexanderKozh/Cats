package com.lex.cat.controller.filter;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Bucket4j;
import io.github.bucket4j.Refill;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;

@Component
public class RateFilter implements Filter {

    @Value("${rate.limit}")
    private Long RATE_LIMIT;

    @Value("${rate.limit.interval-minute}")
    private Long RATE_LIMIT_INTERVAL;

    private Bucket bucket;

    @PostConstruct
    public void init(){
        Bandwidth limit = Bandwidth.classic(
                RATE_LIMIT,
                Refill.greedy(
                        RATE_LIMIT,
                        Duration.ofMinutes(RATE_LIMIT_INTERVAL)
                )
        );

        this.bucket = Bucket4j.builder()
                .addLimit(limit)
                .build();
    }

    @Override
    public void doFilter(
            ServletRequest request,
            ServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {
        if (bucket.tryConsume(1L)) {
            chain.doFilter(request, response);
        } else {
            ((HttpServletResponse) response).sendError(HttpStatus.TOO_MANY_REQUESTS.value());
        }
    }
}
