package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

public class DelayedTransmissionActivity extends AppCompatActivity {

    private DelayedTransmission transmission = new DelayedTransmission();

    private TextView labelMessage;

    private Button sendButton;

    private int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayed_transmission);

        this.sendButton = (Button) findViewById(R.id.sendButton);

        this.labelMessage = (TextView) findViewById(R.id.labelMessage);

        this.transmission = new DelayedTransmission();
        this.transmission.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(byte[] response) {
                // Read the response
                final String data = new String(response);
                final String shortMessage = data.split(System.getProperty("line.separator"), 2)[0];

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        // Add a line describing the transmission in the UI
                        labelMessage.setText(labelMessage.getText() + shortMessage + System.getProperty("line.separator"));
                    }
                });

                // Write the full response to the console
                System.out.println("===== Delayed transmission response : " + data);

                return true;
            }

        });

        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                // Data to compress
                String data = new String("Transmission différée n°" + counter++);

                // Headers for the request
                HashMap<String, List<String>> headers = new HashMap<>();
                headers.put("Content-Type",  Arrays.asList("text/plain"));

                // Send the compressed data
                transmission.sendRequest(
                        "http://sym.iict.ch/rest/txt",
                        headers,
                        data
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });
    }
}
