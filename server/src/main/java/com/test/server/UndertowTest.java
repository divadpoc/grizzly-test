package com.test.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.util.Headers;

import java.io.InputStream;

public class UndertowTest {

    static final ObjectMapper objectMapper = new ObjectMapper();
    static void run() {

        Undertow undertowServer = Undertow.builder()
                .addHttpListener(8081, "localhost")
                .setHandler(exchange -> {
                    if (exchange.getRequestPath().equals("/user") && exchange.getRequestMethod().equalToString("POST")) {
                        // Read JSON from the request body
                        try (InputStream inputStream = exchange.getInputStream()) {
                            User user = objectMapper.readValue(inputStream, User.class);
                            System.out.println("Received User: " + user.name() + ", Age: " + user.age());

                            // Respond with JSON
                            String response = objectMapper.writeValueAsString(user);
                            exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                            exchange.getResponseSender().send(response);
                        } catch (Exception e) {
                            exchange.setStatusCode(500);
                            exchange.getResponseSender().send("Error processing request: " + e.getMessage());
                        }
                    } else {
                        // Handle other paths or methods
                        exchange.setStatusCode(404);
                        exchange.getResponseSender().send("Not Found");
                    }
                })
                .build();

        undertowServer.start();
    }
}
