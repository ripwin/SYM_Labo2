package com.heigvd.sym.labo2;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by mathieu on 19.11.17.
 */

public class DelayedTransmission {


    private CommunicationEventListener cel;
    private OkHttpClient client;
    private Map<String, List<String>> args;

    private List<Request> failedRequests;

    // Whether or not a new try to send failed requests is already scheduled or not
    private boolean isRequestSendingTryScheduled;

    public DelayedTransmission() {
        this.client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .build();

        failedRequests = new ArrayList<>();
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
        sendRequest(req);
    }

    private void sendRequest(Request request) {
        this.client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                System.out.println("An error occured...");
                addFailedRequest(call.request());
                scheduleRequestSending();
                System.out.println("A new try has been scheduled if needed.");
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
     * Schedule a new try for sending new requests. If a new try has already been scheduled and
     * hasn't been completed yet, the method doesn't do anything.
     */
    private synchronized void scheduleRequestSending() {
        if (!isRequestSendingTryScheduled) {
            new Timer().schedule(new TimerTask() {
                @Override
                public void run() {
                    retryFailedRequests();
                }
            }, 10000);
            isRequestSendingTryScheduled = true;
        }
    }

    /**
     * Add a request to our list of failed requests.
     * @param request the request to try to send later
     */
    private synchronized void addFailedRequest(Request request) {
        System.out.println("Adding a request to failed requests");
        failedRequests.add(request);
    }

    /**
     * Tries to send failed requests again. This method sends every failed request in our list of
     * failed requests. It also sets reset the isRequestSendingTryScheduled to false.
     */
    private synchronized void retryFailedRequests() {
        System.out.println("Sending failed requests again...");
        for (Request r : failedRequests) {
            sendRequest(r);
        }

        failedRequests.clear();
        isRequestSendingTryScheduled = false;
    }


    public void setCommunicationEventListener(CommunicationEventListener cel) {
        this.cel = cel;
    }


}
