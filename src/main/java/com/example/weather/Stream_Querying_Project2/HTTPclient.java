package com.example.weather.Stream_Querying_Project2;

import com.example.weather.Stream_Querying_Project2.controller.MessageController;
import com.example.weather.Stream_Querying_Project2.kafka.KafkaProducer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

//I created this class to connect to the kafka publish endpoint and actually post the
//message.
public class HTTPclient {

    private static String apiUrl = "http://localhost:8080/api/v1/kafka/publish";

    public static void connect(String topic, String message)
    {
        MessageController.topic = topic;
        try {
            String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8.toString());
            URL url = new URL(apiUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json; utf-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoOutput(true);
            //Here, I am writing JSON data to the connection output stream
            try(OutputStream os = conn.getOutputStream()) {
                byte[] input = message.getBytes("utf-8");
                os.write(input, 0, input.length);
            }

            //This reads the response from the server
            try(BufferedReader br = new BufferedReader(
                    new InputStreamReader(conn.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
                System.out.println(response.toString());
            }

            conn.disconnect();
        }
        catch(Exception e){
            e.printStackTrace();
        }

    }


}
