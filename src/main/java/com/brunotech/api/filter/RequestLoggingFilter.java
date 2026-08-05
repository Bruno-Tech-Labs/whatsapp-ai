package com.brunotech.api.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class RequestLoggingFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(RequestLoggingFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        if ("POST".equalsIgnoreCase(request.getMethod()) && request.getRequestURI().contains("/webhook")) {
            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
            byte[] body = cachedRequest.getCachedBody();
            String payload = new String(body, StandardCharsets.UTF_8);

            log.info("[Webhook Debug] request-uri={} method={} signature={}",
                    request.getRequestURI(),
                    request.getMethod(),
                    request.getHeader("X-Hub-Signature-256"));
            log.info("[Webhook Debug] raw-body={}", payload);

            filterChain.doFilter(cachedRequest, response);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
