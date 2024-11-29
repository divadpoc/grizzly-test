package com.test.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.undertow.Undertow;
import io.undertow.server.HttpHandler;
import io.undertow.server.HttpServerExchange;
import io.undertow.util.Headers;

import java.io.InputStream;

public class UndertowTest {

    static final ObjectMapper objectMapper = new ObjectMapper();
    static void run() {

        Undertow undertowServer = Undertow.builder()
                .addHttpListener(8081, "localhost", new HttpHandler() {
                    @Override
                    public void handleRequest(HttpServerExchange exchange) throws Exception {
                        if (exchange.isInIoThread()) {
                            // Dispatch to worker thread if needed
                            exchange.dispatch(this);
                            return;
                        }
                        if (exchange.getRequestPath().equals("/user") && exchange.getRequestMethod().equalToString("POST")) {
                            exchange.getRequestReceiver().receiveFullBytes((ex, data) -> {
                                String body = new String(data);
                                System.out.println("Received: " + body);

                                // Respond to the client
                                ex.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
                                ex.getResponseSender().send("{\"status\":\"success\"}");
                            });
//                            // Read JSON from the request body
//                            try (InputStream inputStream = exchange.getInputStream()) {
//                                User user = objectMapper.readValue(inputStream, User.class);
//                                System.out.println("Received User: " + user.name() + ", Age: " + user.age());
//
//                                // Respond with JSON
//                                String response = objectMapper.writeValueAsString(user);
//                                exchange.getResponseHeaders().put(Headers.CONTENT_TYPE, "application/json");
//                                exchange.getResponseSender().send(response);
//                            } catch (Exception e) {
//                                System.out.println();
//                                System.out.println("undertow");
//                                e.printStackTrace();
//                                exchange.setStatusCode(500);
//                                exchange.getResponseSender().send("Error processing request: " + e.getMessage());
//                            }
                        } else {
                            // Handle other paths or methods
                            exchange.setStatusCode(404);
                            exchange.getResponseSender().send("Not Found");
                        }
                    }
                })
                .build();

        undertowServer.start();
    }
}
