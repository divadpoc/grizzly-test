package com.test.client;

public class Main {

    private static final String endpointGrizzly = "http://localhost:8080/user";
    private static final String endpointUndertow = "http://localhost:8081/user";
    public static final String endpoint = endpointGrizzly;

    public static void main(String[] args) {

        // JSON data to send
        String jsonData = """
                {
                    "name": "John Doe",
                    "email": "john.doe@example.com",
                    "age": 30
                }
                """;

        System.out.println("Hello, World!");
        Java.run(endpointGrizzly, jsonData);
        Java.run(endpointUndertow, jsonData);

        Apache.run(endpointGrizzly, jsonData);
        Apache.run(endpointUndertow, jsonData);
    }

}