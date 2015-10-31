package edu.regis.msse655.scis.service.tasks;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Tim on 10/31/15.
 */
public abstract class HttpTask extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... params) {
        StringBuilder result = new StringBuilder();
        HttpURLConnection urlConnection = null;
        try {
            URL url = new URL(params[0]);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestProperty("Accept", "application/json");
            InputStream inputStream = new BufferedInputStream((urlConnection.getInputStream()));
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
        } catch (IOException e) {
            Log.i("HttpTask", "EXCEPTION: " + e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
        }
        return result.toString();
    }

}