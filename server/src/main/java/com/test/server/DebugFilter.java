package com.test.server;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@Provider
public class DebugFilter implements ContainerRequestFilter, ContainerResponseFilter {

    private void log(String message) {
        System.out.println(message);
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        log("Incoming Request: ");
        log("URI: " + requestContext.getUriInfo().getRequestUri());
        log("Method: " + requestContext.getMethod());
        log("Headers: " + requestContext.getHeaders());
    }

    @Override
    public void filter(ContainerRequestContext requestContext, ContainerResponseContext responseContext) throws IOException {
        log("Outgoing Response: ");
        log("Status: " + responseContext.getStatus());
        log("Headers: " + responseContext.getHeaders());
    }
}