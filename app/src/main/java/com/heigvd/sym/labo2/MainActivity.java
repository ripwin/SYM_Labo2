package com.heigvd.sym.labo2;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    private Button asyncButton;
    private Button delayedButton;
    private Button objectButton;
    private Button compressedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init Async Button
        this.asyncButton = (Button) findViewById(R.id.asyncButton);
        this.asyncButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AsyncTransmissionActivity.class);
                startActivity(intent);
            }
        });

        // Init Delayed Button
        this.delayedButton = (Button) findViewById(R.id.delayedButton);
        this.delayedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DelayedTransmissionActivity.class);
                startActivity(intent);
            }
        });

        // Init Object Button
        this.objectButton = (Button) findViewById(R.id.objectButton);
        this.objectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ObjectTransmissionActivity.class);
                startActivity(intent);
            }
        });

        // Init Compressed Button
        this.compressedButton = (Button) findViewById(R.id.compressedButton);
        this.compressedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CompressedTransmissionActivity.class);
                startActivity(intent);
            }
        });


    }
}
