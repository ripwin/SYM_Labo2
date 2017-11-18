package com.heigvd.sym.labo2;

import android.app.AuthenticationRequiredException;
import android.webkit.HttpAuthHandler;

import java.net.HttpURLConnection;
import java.util.EventListener;

/**
 * Created by User on 26.10.2017.
 */

public interface CommunicationEventListener  extends EventListener {
    public boolean handleServerResponse(byte[] response);
}
