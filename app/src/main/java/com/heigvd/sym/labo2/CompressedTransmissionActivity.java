package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

public class CompressedTransmissionActivity extends AppCompatActivity {

    private AsyncTransmission asyncTransmission = new AsyncTransmission();
    private RadioButton rbText;
    private RadioButton rbJson;
    private RadioButton rbXml;

    private Button bSend;
    private TextView textResponse;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compressed_transmission);
        this.rbText = (RadioButton) findViewById(R.id.Text);
        this.rbJson = (RadioButton) findViewById(R.id.Json);
        this.rbXml = (RadioButton) findViewById(R.id.XML);
        this.textResponse = (TextView) findViewById(R.id.textResponse);
        this.bSend = (Button) findViewById(R.id.sendButton);
        this.url = "http://sym.iict.ch/";
    }
}
