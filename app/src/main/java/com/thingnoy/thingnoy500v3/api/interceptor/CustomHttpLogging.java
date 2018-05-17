package com.thingnoy.thingnoy500v3.api.interceptor;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;

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
//            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
//                    .tag(logName)
//                    .showThreadInfo(true)
//                    .methodCount(1)
//                    .showThreadInfo(false)
//                    .build();
//            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
//            Logger.json(prettyPrintJson);
//            Log.d(logName, prettyPrintJson);

            System.out.println(prettyPrintJson);

        } catch (JsonSyntaxException m) {
            Log.e(TAG, "html header parse failed");
            m.printStackTrace();
            Log.e(logName, message);
        }
    }

//    @Override
//    public void log(String message) {
//        final String logName = "OkHttp";
//        if (!message.startsWith("{")) {
//            Log.d(logName, message);
//            return;
//        }
//        try {
//            String prettyPrintJson = new GsonBuilder()
//                    .setPrettyPrinting()
//                    .create()
//                    .toJson(new JsonParser().parse(message));
//            // use Logger
////            Logger.init().methodCount( 1 ).hideThreadInfo();
////            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
////                    .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
////                    .methodCount(0)         // (Optional) How many method line to show. Default 2
////                    .methodOffset(7)        // (Optional) Hides internal method calls up to offset. Default 5
//////                .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
////                    .tag(logName)   // (Optional) Global tag for every log. Default PRETTY_LOGGER
////                    .build();
//
////            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
//            FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
////                    .tag(logName)
//                    .showThreadInfo(true)
//                    .methodCount(1)
//                    .showThreadInfo(false)
//                    .build();
//            Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy));
//            Logger.t(logName).json(prettyPrintJson);
////            Log.d(logName, prettyPrintJson);
//        } catch (JsonSyntaxException m) {
//            Log.e(TAG, "html header parse failed");
//            m.printStackTrace();
//            Log.e(logName, message);
//        }
//    }
}
