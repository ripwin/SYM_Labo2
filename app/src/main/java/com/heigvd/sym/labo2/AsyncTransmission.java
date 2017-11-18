package com.heigvd.sym.labo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONStringer;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by User on 26.10.2017.
 */

public class AsyncTransmission {

    private CommunicationEventListener cel;
    private final OkHttpClient client;
    private Map<String, List<String>> args;

    public AsyncTransmission() {

        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();
    }

    /**
     * Send the request to the server
     *
     * @param headers map contening <header, <Content-type, type>>
     * @param url  server url
     * @return
     * @throws Exception
     */
    public void sendRequest(String url, Map<String, List<String>> headers, String message)
        throws IllegalArgumentException, IOException
    {
        sendRequest(url, headers, message.getBytes());
    }

    public void sendRequest(String url, Map<String, List<String>> headers, byte[] message)
            throws IllegalArgumentException, IOException
    {
        // Check URL
        if (url.isEmpty()) {
            throw new IllegalArgumentException("URL cannot be empty");
        }

        RequestBody body = RequestBody.create(null, message);

        Request.Builder builder = new Request.Builder();
        builder.url(url);
        builder.post(body);

        for(Map.Entry<String, List<String>> e : headers.entrySet()) {

            // Concat value(s)
            String value = new String();
            if(!e.getValue().isEmpty()) {
                value = e.getValue().get(0);
                for(int i = 1; i < e.getValue().size(); i++) {
                    value += "," + e.getValue().get(i);
                }
            }

            // Add key and value(s)
            for(String header : e.getValue()) {
                builder.header(e.getKey(), value);
            }
        }

        Request req = builder.build();

        this.client.newCall(req).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // Do something when request failed
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    //throw new IOException("Error : " + response);
                    throw new IOException("Error : " + response);
                } else {
                    // Read data in the worker thread
                    final byte[] data = response.body().bytes();
                    cel.handleServerResponse(data);
                }
            }
        });
    }

    /**
     * Add listener to call when response it's received
     *
     * @param cel listener
     */
    public void setCommunicationEventListener(CommunicationEventListener cel) {
        this.cel = cel;
    }

}
