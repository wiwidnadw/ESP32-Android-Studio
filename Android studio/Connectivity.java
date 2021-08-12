package com.example.flowcalibration2;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class Connectivity {
    public static String geturl (String url_esp32){

        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url(url_esp32)
                .build();

        try  {
            Response response = client.newCall(request).execute();
            return response.body().string();

        } catch (IOException error) {
            return error.toString();
        }


    }
}
