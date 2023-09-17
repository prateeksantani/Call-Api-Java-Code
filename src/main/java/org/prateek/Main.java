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
        String urlString = "https://api.zippopotam.us/us/33162";

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
            String postCode = jsonResponse.getString("post code");
            String country = jsonResponse.getString("country");
            String countryAbbreviation = jsonResponse.getString("country abbreviation");
            JSONArray placesArray = jsonResponse.getJSONArray("places");
            JSONObject placeObject = placesArray.getJSONObject(0);
            String placeName = placeObject.getString("place name");
            String longitude = placeObject.getString("longitude");
            String state = placeObject.getString("state");
            String stateAbbreviation = placeObject.getString("state abbreviation");
            String latitude = placeObject.getString("latitude");

            // Create a new JSON object to store the extracted data
            JSONObject extractedData = new JSONObject();
            extractedData.put("post code", postCode);
            extractedData.put("country", country);
            extractedData.put("country abbreviation", countryAbbreviation);

            JSONArray places = new JSONArray();
            JSONObject place = new JSONObject();
            place.put("place name", placeName);
            place.put("longitude", longitude);
            place.put("state", state);
            place.put("state abbreviation", stateAbbreviation);
            place.put("latitude", latitude);
            places.put(place);

            extractedData.put("places", places);

            // Print the extracted JSON data
            System.out.println(extractedData.toString());
        } else {
            System.out.println("API call could not be made!!!");
        }
    }
}
