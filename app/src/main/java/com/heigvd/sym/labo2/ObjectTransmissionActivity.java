package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ObjectTransmissionActivity extends AppCompatActivity {

    private Button jsonButton;
    private Button xmlButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_transmission);

        this.jsonButton = (Button) findViewById(R.id.jsonButton);
        this.xmlButton = (Button) findViewById(R.id.xmlButton);


        // Json button action
        this.jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
