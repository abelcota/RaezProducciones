package com.visionsoft.raezproducciones;

import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class GetJSONAbonos extends AsyncTask<String, Void, response_abonos>{
        private ListenerAbonos aListener;

        public GetJSONAbonos(ListenerAbonos listener) {
            aListener = listener;
        }

        public interface ListenerAbonos {
            void onLoaded(List<abonos> abonosList);
            void onError();
        }

    @Override
    protected response_abonos doInBackground(String... strings) {
        try {
            String stringResponse = loadJSON(strings[0]);
            Gson gson = new Gson();
            return gson.fromJson(stringResponse, response_abonos.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return null;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(response_abonos response) {
        if (response != null) {
            aListener.onLoaded(response.getAbonos());
        } else {
            aListener.onError();
        }
    }

    private String loadJSON(String jsonURL) throws IOException {
        URL url = new URL(jsonURL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setReadTimeout(10000);
        conn.setConnectTimeout(15000);
        conn.setRequestMethod("GET");
        conn.setDoInput(true);
        conn.connect();
        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
        String line;
        StringBuilder response = new StringBuilder();
        while ((line = in.readLine()) != null) {

            response.append(line);
        }
        in.close();
        return response.toString();
    }

}
