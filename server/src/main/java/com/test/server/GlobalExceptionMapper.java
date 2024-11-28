package com.test.server;

import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Throwable> {

    public GlobalExceptionMapper() {
        System.out.println("GlobalExceptionMapper initialized");
    }

    @Override
    public Response toResponse(Throwable e) {

        // Log the exception with stack trace
        System.err.println("Unhandled exception occurred - " + e.getMessage());
        e.printStackTrace();

        // Return a generic error response to the client
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity("An unexpected error occurred: " + e.getMessage())
                .build();
    }

}
