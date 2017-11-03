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
import java.util.List;
import java.util.Map;

public class AsyncTransmissionActivity extends AppCompatActivity {

    private AsyncTransmission asyncTransmission = new AsyncTransmission();
    private Button sendButton;
    private TextView urlTextView;
    private TextView requestTextView;
    private TextView responseTextView;

    private Map<String, List<String>> args;
    private String url;
    private String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            url = savedInstanceState.getString("url");
            message = savedInstanceState.getString("message");
        }
        this.args = new ArrayMap<>();

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
                message= requestTextView.getText().toString();

                if(url.isEmpty() || message.isEmpty() ){
                  // popu
                }else{
                    asyncTransmission.setCommunicationEventListener(new CommunicationEventListener() {
                        @Override
                        public boolean handleServerResponse(String response) {
                            args.clear();
                            if(response.isEmpty()){
                                responseTextView.setText("message empty");
                                return false;
                            }
                            responseTextView.setText(response);
                            return true;
                        }
                    });

                    try {
                        args.put("header",  Arrays.asList("Content-type:", "text/plain") );
                        args.put("body", Arrays.asList(message));
                        asyncTransmission.sendRequest(url, args);
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
