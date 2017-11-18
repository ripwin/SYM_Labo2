package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import java.util.zip.Inflater;

public class CompressedTransmissionActivity extends AppCompatActivity {

    private AsyncTransmission transmission = new AsyncTransmission();

    private Button sendButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compressed_transmission);

        this.sendButton = (Button) findViewById(R.id.sendButton);

        this.transmission = new AsyncTransmission();
        this.transmission.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(byte[] response) {
            try {
                // Get the compressed data
                byte[] decompressedData = decompress(response);

                System.out.println("OUTPUT : " + new String(decompressedData));

            } catch (DataFormatException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return true;
            }

        });

        this.sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            try {
                // Data to compress
                String data = new String("Bonjour mon nom est Android");
                System.out.println("INPUT : " + data);
                byte[] compressedData = compress(data.getBytes());

                // Headers for the request
                HashMap<String, List<String>> headers = new HashMap<>();
                headers.put("Network", Arrays.asList("CSD"));
                headers.put("Content-Encoding",  Arrays.asList("deflate"));
                headers.put("Content-Type", Arrays.asList("application/zip"));

                // Send the compressed data
                transmission.sendRequest(
                    "http://sym.iict.ch/rest/txt",
                    headers,
                    compressedData
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        });
    }

    /**
     * Compress byte data with deflater
     *
     * @param data The data to compress
     * @return The compressed data in byte
     * @throws IOException
     */
    private byte[] compress(byte[] data) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Deflater deflater = new Deflater(Deflater.BEST_COMPRESSION, true);
        DeflaterOutputStream deflaterStream = new DeflaterOutputStream(stream, deflater);
        deflaterStream.write(data);
        deflaterStream.finish();

        return stream.toByteArray();
    }

    /**
     * Decompress byte data with inflater
     *
     * @param data The data to decompress
     * @return The decompressed data in byte
     * @throws DataFormatException
     * @throws IOException
     */
    private byte[] decompress(byte[] data) throws DataFormatException, IOException {
        Inflater inflater = new Inflater(true);
        inflater.setInput(data);

        ByteArrayOutputStream stream = new ByteArrayOutputStream(data.length);

        byte[] buffer = new byte[1024];
        while(!inflater.finished()) {
            int count = inflater.inflate(buffer);
            stream.write(buffer, 0, count);
        }

        stream.close();

        return stream.toByteArray();
    }
}
