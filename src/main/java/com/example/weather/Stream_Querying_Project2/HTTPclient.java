package com.example.weather.Stream_Querying_Project2;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HTTPclient {
    private static String apiUrl = "http://localhost:8080/api/v1/kafka/publish";

    public static void connect(String message){
        try {
            URL url = new URL(apiUrl + "?message=" + message);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            int responseCode = conn.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            if (responseCode == HttpURLConnection.HTTP_OK) {
                System.out.println("Message sent successfully");
            } else {
                System.out.println("Failed to send message");
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }
}
