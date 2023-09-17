package org.prateek;

import org.json.JSONArray;
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
        String urlString = "https://api.nationalize.io/?name=nathaniel";

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

            // Parse the JSON response correctly
            JSONObject jsonResponse = new JSONObject(apiData.toString());
            JSONArray countryArray = jsonResponse.getJSONArray("country");

            // Create a new JSON array to store the extracted data
            JSONArray extractedData = new JSONArray();

            for (int i = 0; i < countryArray.length(); i++) {
                JSONObject countryObject = countryArray.getJSONObject(i);
                String countryName = countryObject.getString("country_id");
                double probability = countryObject.getDouble("probability");

                // Create a new JSON object for each country
                JSONObject countryData = new JSONObject();
                countryData.put("country_id", countryName);
                countryData.put("probability", probability);

                // Add the country data to the extracted data array
                extractedData.put(countryData);
            }

            // Print the extracted JSON data
            System.out.println(extractedData.toString());
        } else {
            System.out.println("API call could not be made!!!");
        }
    }
}
