package com.thingnoy.thingnoy500v3.manager.http;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by HBO on 17/9/2560.
 */

public class CustomHttpLogging implements HttpLoggingInterceptor.Logger{
    @Override
    public void log(String message) {
        String logName = "OkHttpSkypart";
        if (!message.startsWith("{")){
            Log.d(logName,message);
            return;
        }
        try {
            String prettyPrintJson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(new JsonParser().parse(message));
            Log.d(logName,prettyPrintJson);
        }catch (JsonSyntaxException m){
            Log.d(logName,message);
        }
    }
}
