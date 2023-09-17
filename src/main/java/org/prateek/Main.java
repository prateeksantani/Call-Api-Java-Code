package org.prateek;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Main {
    public static void main(String[] args) throws IOException {

        URL url = null;
        HttpURLConnection connection = null;
        int responseCode = 0;
        String urlString = "https://api.chucknorris.io/jokes/random";

        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            System.out.println("Problem with URL");
        }

        // Make the API call
        try {
            connection = (HttpURLConnection) url.openConnection();
            responseCode = connection.getResponseCode();
        } catch (Exception e) {
            System.out.println("Connection problem");
        }

        // Extract and parse the JSON response
        if (responseCode == 200) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder apiData = new StringBuilder();
            String readLine;

            while ((readLine = in.readLine()) != null) {
                apiData.append(readLine);
            }

            in.close();

            JSONObject jsonAPIResponse = new JSONObject(apiData.toString());

            // Access the Chuck Norris joke from the JSON response
            String joke = jsonAPIResponse.getString("value");
            System.out.println("Chuck Norris Joke: " + joke);
        } else {
            System.out.println("API call could not be made!!!");
        }
    }
}
