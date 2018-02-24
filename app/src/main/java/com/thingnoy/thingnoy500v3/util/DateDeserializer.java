package com.thingnoy.thingnoy500v3.util;

import android.annotation.SuppressLint;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by HBO on 12/2/2561.
 */

public class DateDeserializer implements JsonDeserializer<Date> {


    @Override
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String date = json.getAsString();

        @SuppressLint("SimpleDateFormat") SimpleDateFormat formatter = new SimpleDateFormat("M/d/yy");
        formatter.setTimeZone(TimeZone.getTimeZone("UTC"));

//        try {
//            return formatter.parse(date);
//        } catch (ParseException e) {
////            System.err.println("Failed to parse Date due to:", e);
//            return null;
//        }
        try {
            return formatter.parse(date);
        } catch (ParseException e) {
            throw new JsonParseException(e);
        }
    }
}

