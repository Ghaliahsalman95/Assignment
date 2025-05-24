package com.example.movietwebapplication.Config;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.Duration;

public class RateLimitFilter implements Filter {
//• Be aware of API limit which is 40 Requests per 10 seconds.
    private final Bucket bucket;

    public RateLimitFilter() {
        Bandwidth limit = Bandwidth.classic(40, Refill.greedy(40, Duration.ofSeconds(10)));
        this.bucket = Bucket.builder().addLimit(limit).build();
    }

    @Override  // i have one filter
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (bucket.tryConsume(1)) {  //the request in interval (40 in 10 sec)
            chain.doFilter(request, response);
        } else {
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            httpResponse.setStatus(429); // many requests
            httpResponse.getWriter().write("many requests please wait.");
        }
    }
}
