package com.test.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        try {
            // Create the HTTP client
            HttpClient client = HttpClient.newBuilder()
                    .connectTimeout(Duration.ofSeconds(10)) // Set connection timeout
                    .build();

            // JSON data to send
            String jsonData = """
                {
                    "name": "John Doe",
                    "email": "john.doe@example.com",
                    "age": 30
                }
                """;

            // Create the HTTP POST request
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:8080/user"))
                    .timeout(Duration.ofSeconds(10)) // Set request timeout
                    .header("Content-Type", "application/json") // Set header for JSON
                    .POST(HttpRequest.BodyPublishers.ofString(jsonData)) // Attach JSON data
                    .build();

            // Send the request and handle the response
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            // Print response status and body
            System.out.println("Response status: " + response.statusCode());
            System.out.println("Response body: " + response.body());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}