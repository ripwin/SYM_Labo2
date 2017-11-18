package com.heigvd.sym.labo2;


import android.support.v4.util.ArrayMap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AsyncTransmissionActivity extends AppCompatActivity {

    private AsyncTransmission asyncTransmission = new AsyncTransmission();
    private Button sendButton;
    private TextView urlTextView;
    private TextView requestTextView;
    private TextView responseTextView;

    private String url;
    private String message;

    private Thread runOnUiThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString("url");
            message = savedInstanceState.getString("message");
        }

        setContentView(R.layout.activity_async_transmission);

        this.urlTextView = (TextView) findViewById(R.id.urlText);
        this.requestTextView = (TextView) findViewById(R.id.requestText);
        this.responseTextView = (TextView) findViewById(R.id.responseText);

        // Init Async Button
        this.sendButton = (Button) findViewById(R.id.sendButton);
        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url = urlTextView.getText().toString();
                message = requestTextView.getText().toString();

                if (url.isEmpty() || message.isEmpty()) {
                    // popu
                } else {
                    asyncTransmission.setCommunicationEventListener(new CommunicationEventListener() {
                        @Override
                        public boolean handleServerResponse(final byte[] response) {

                            final String data = new String(response);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {


                                    if (data.isEmpty()) {
                                        responseTextView.setText("message empty");

                                    }
                                    else {
                                        responseTextView.setText(data);
                                    }
                                }
                            });

                            if(data.isEmpty()) {
                                return false;
                            }

                            return true;
                        }
                    });

                    try {

                        HashMap<String, List<String>> headers = new HashMap<>();
                        headers.put("Content-Type",  Arrays.asList("text/plain"));

                        asyncTransmission.sendRequest(url, headers, message);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putString("url", url);
        outState.putString("message", message);
        super.onSaveInstanceState(outState);
    }
}
