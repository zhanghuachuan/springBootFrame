package com.huachuan.utils;

import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ChatGPTClient {
    private static final String API_KEY = "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";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";
    private final static Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("127.0.0.1", 7890));
    public static String chatWithGPT(String message) {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection(proxy);
            connection.setRequestMethod("POST");
            connection.setConnectTimeout(10 * 1000);
            connection.setRequestProperty("Authorization", "Bearer " + API_KEY);
            connection.setRequestProperty("Content-Type", "application/json");

            connection.setDoOutput(true);

            JsonObject jsonInput = new JsonObject();
            jsonInput.addProperty("prompt", message);
            jsonInput.addProperty("max_tokens", 2048);
            jsonInput.addProperty("temperature",0);
            jsonInput.addProperty("model", "gpt-3.5-turbo");

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(jsonInput.toString().getBytes());

            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();

            JsonObject jsonResponse = JsonParser.parseString(response.toString()).getAsJsonObject();
            return jsonResponse.getAsJsonArray("choices").get(0).getAsJsonObject().get("text").getAsString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error: " + e.getMessage();
        }
    }

    public static void main(String[] args) {
        String message = "你好，ChatGPT";
        String response = chatWithGPT(message);
        System.out.println("ChatGPT回答: " + response);
    }
}