package com.heigvd.sym.labo2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.widget.Button;

import com.google.gson.Gson;
import com.heigvd.sym.labo2.com.heigvd.sym.labo2.objects.Person;
import com.heigvd.sym.labo2.com.heigvd.sym.labo2.objects.Phone;

import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.io.StringWriter;
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
        this.transmission.setCommunicationEventListener(new CommunicationEventListener() {
            @Override
            public boolean handleServerResponse(String response) {
                System.out.println("Output object : " + response);
                return true;
            }
        });

        // Json button action
        this.jsonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            Person p = new Person("Jack", "Jones", Person.Gender.Male, new Phone("0210000000", Phone.Type.Home));

            Gson gson = new Gson();
            String json = gson.toJson(p);

            System.out.println("Input object : " + json);

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
            Person p = new Person("Jack", "Jones", Person.Gender.Male, new Phone("0210000000", Phone.Type.Home));

            XmlSerializer serializer = Xml.newSerializer();
            StringWriter writer = new StringWriter();

            try {
                serializer.setOutput(writer);
                serializer.startDocument("UTF-8", null);
                serializer.setFeature("http://xmlpull.org/v1/doc/features.html#indent-output", true);
                serializer.docdecl(" directory SYSTEM \"http://sym.iict.ch/directory.dtd\"");


                serializer.startTag("", "directory");
                serializer.startTag("", "person");

                serializer.startTag("", "name");
                serializer.text(p.getName());
                serializer.endTag("", "name");

                serializer.startTag("", "firstname");
                serializer.text(p.getFirstName());
                serializer.endTag("", "firstname");

                if(!p.getMiddleName().isEmpty()) {
                    serializer.startTag("", "middlename");
                    serializer.text(p.getMiddleName());
                    serializer.endTag("", "middlename");
                }

                serializer.startTag("", "gender");
                serializer.text(p.getGender());
                serializer.endTag("", "gender");

                for(Phone phone : p.getPhones()) {
                    serializer.startTag("", "phone");
                    serializer.attribute("", "type", phone.type());
                    serializer.text(phone.number());
                    serializer.endTag("", "phone");
                }

                serializer.endTag("", "person");
                serializer.endTag("", "directory");

                serializer.endDocument();

                System.out.println("Output object : " + writer.toString());

                HashMap<String, List<String>> headers = new HashMap<>();
                headers.put("Content-Type",  Arrays.asList("application/xml"));

                try {
                    transmission.sendRequest("http://sym.iict.ch/rest/xml", headers, writer.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
            catch(IOException e) {
                e.printStackTrace();
            }

            }
        });
    }
}
