package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CompressedTransmissionActivity extends AppCompatActivity {

    private AsyncTransmission asyncTransmission = new AsyncTransmission();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compressed_transmission);
    }
}
