package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.heigvd.sym.labo2.com.heigvd.sym.labo2.objects.Person;
import com.heigvd.sym.labo2.com.heigvd.sym.labo2.objects.Phone;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class ObjectTransmissionActivity extends AppCompatActivity {

    private Button jsonButton;
    private Button xmlButton;

    private AsyncTransmission transmission;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_transmission);

        this.jsonButton = (Button) findViewById(R.id.jsonButton);
        this.xmlButton = (Button) findViewById(R.id.xmlButton);

        this.transmission = new AsyncTransmission();

        // Json button action
        this.jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Person p = new Person("Jack", "Jones", Person.Gender.Male, new Phone("0210000000", Phone.Type.Home));

            Gson gson = new Gson();
            String json = gson.toJson(p);

            System.out.println("Input object : " + json);

            transmission.setCommunicationEventListener(new CommunicationEventListener() {
                @Override
                public boolean handleServerResponse(String response) {
                    System.out.println("Output object : " + response);
                    return true;
                }
            });

            HashMap<String, List<String>> headers = new HashMap<>();
            headers.put("Content-Type",  Arrays.asList("application/json"));
            headers.put("Accept", Arrays.asList("application/json"));

            try {
                transmission.sendRequest("http://sym.iict.ch/rest/json", headers, json);
            } catch (Exception e) {
                e.printStackTrace();
            }
            }
        });


        // Xml button action
        this.xmlButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
