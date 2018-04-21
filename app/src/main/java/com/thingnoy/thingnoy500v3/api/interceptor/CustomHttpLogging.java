package com.thingnoy.thingnoy500v3.api;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by HBO on 17/9/2560.
 */

public class CustomHttpLogging implements HttpLoggingInterceptor.Logger {
    private final static String TAG = CustomHttpLogging.class.getSimpleName();

    @Override
    public void log(String message) {
        final String logName = "OkHttp";
        if (!message.startsWith("{")) {
            Log.d(logName, message);
            return;
        }
        try {
            String prettyPrintJson = new GsonBuilder()
                    .setPrettyPrinting()
                    .create()
                    .toJson(new JsonParser().parse(message));
            // use Logger
            Logger.addLogAdapter(new AndroidLogAdapter());
            Logger.t(logName).json(prettyPrintJson);
//            Log.d(logName, prettyPrintJson);
        } catch (JsonSyntaxException m) {
            Log.e(TAG, "html header parse failed");
            m.printStackTrace();
            Log.e(logName, message);
        }
    }
}
