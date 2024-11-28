package com.test.server;


import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.net.URI;

public class Main {

    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        // Create a ResourceConfig and register the Jackson JSON provider
        final ResourceConfig config = new ResourceConfig()
                .packages("com.test.server.rest")
                .register(DebugFilter.class)
                .register(GlobalExceptionMapper.class)
                .register(org.glassfish.jersey.jackson.JacksonFeature.class);


        // Start the server with the configuration
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), config);
    }
    public static void main(String[] args) {
        final HttpServer server = startServer();
        System.out.println("Server started at " + BASE_URI);
        Runtime.getRuntime().addShutdownHook(new Thread(server::shutdownNow));
    }
}