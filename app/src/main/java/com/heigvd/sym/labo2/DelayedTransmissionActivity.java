package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;

public class DelayedTransmissionActivity extends AppCompatActivity {

    private DelayedTransmission transmission = new DelayedTransmission();

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delayed_transmission);

        this.sendButton = (Button) findViewById(R.id.sendButton);

        this.transmission = new DelayedTransmission();
        this.transmission.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(byte[] response) {

            System.out.println("OUTPUT : " + new String(response));

            return true;
            }

        });

        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                // Data to compress
                String data = new String("Bonjour, ceci est une transimission différée");
                System.out.println("Transmission différée : " + data);

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
                System.out.println("LOOOOOOL : ");
                e.printStackTrace();
            }
            }
        });
    }
}
