package it.ralucamb.laboratoriomobile.italiancities.utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import it.ralucamb.laboratoriomobile.italiancities.models.City;

public class Requests {

    public static void asyncRequest(String address, RequestCallback callback) {
        new Thread(() -> {
           // String result = doRequest("https://comuni-ita.herokuapp.com/api/comuni");
            String result= doRequest(address);
            List<City> data = new ArrayList<>();
            try{
                JSONArray array = new JSONArray(result);
                for(int i = 0; i < array.length(); i++) {
                    JSONObject item = array.getJSONObject(i);
                    City city = Parser.parseCity(item);
                    if(city != null) data.add(city);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            if(callback != null) callback.onCompleted(data);
        }).start();

    }

    private static String doRequest(String address) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            int code = connection.getResponseCode();
            InputStream inputStream = code == HttpURLConnection.HTTP_OK ? connection.getInputStream() : connection.getErrorStream();

            StringBuilder sb = new StringBuilder();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) connection.disconnect();
        }
        return null;
    }
}