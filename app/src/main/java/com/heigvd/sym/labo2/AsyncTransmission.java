package com.heigvd.sym.labo2;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONStringer;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by User on 26.10.2017.
 */

public class AsyncTransmission   {

    private CommunicationEventListener cel;
    private final OkHttpClient client ;

    public AsyncTransmission(){
        this.client = new OkHttpClient();
    }

    /**
     * Send the request to the server
     * @param request document
     * @param url server url
     * @return
     * @throws Exception
     */
    public void sendRequest(String request, String url) throws Exception {

        RequestBody bodyRequest = RequestBody.create(MediaType.parse(request), request);

        Request req = new Request.Builder()
                .url(url)
                .header("Content-type:", "text/plain")
                .post(bodyRequest)
                .build();

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
                    final String data = response.body().string();
                    cel.handleServerResponse(data);
                }
            }
        });
    }


    /**
     * Add listener to call when response it's received
     * @param cel listener
     */
    public void setCommunicationEventListener (CommunicationEventListener cel){
        this.cel = cel;
    }

}
