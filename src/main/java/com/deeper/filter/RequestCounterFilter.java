package com.deeper.filter;

import org.slf4j.Logger;
import java.io.IOException;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.deeper.controller.GeometryController;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

@Component
public class RequestCounterFilter implements Filter {

    private static final Logger logger = LoggerFactory.getLogger(RequestCounterFilter.class);

    @Autowired
    private GeometryController geometryController;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("RequestCounterFilter initialized.");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        logger.info("RequestCounterFilter: Request received.");
        geometryController.incrementRequestCount();
        geometryController.incrementActiveRequests();

        try {
            chain.doFilter(request, response);
        } finally {
            geometryController.decrementActiveRequests();
            logger.info("Request processing completed. Active requests: {}", geometryController.getRequestCount());
        }
    }

    @Override
    public void destroy() {
        logger.info("RequestCounterFilter destroyed.");
    }
}