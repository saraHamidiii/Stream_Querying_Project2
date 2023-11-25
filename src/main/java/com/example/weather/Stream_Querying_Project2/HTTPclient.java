package com.example.weather.Stream_Querying_Project2;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class HTTPclient {

    //http:localhost:8080/api/v1/kafka/publish?message=hello world
    private static String apiUrl = "http://localhost:8080/api/v1/kafka/publish";

    public static void connect(String message){
        try {
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            URL url = new URL(apiUrl + "?message=" + encodedMessage);
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
