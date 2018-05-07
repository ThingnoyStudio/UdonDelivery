package com.thingnoy.thingnoy500v3.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;

public class GetPrettyPrintJson {

    public String getJson(Object object){
        String strItem = new Gson()
                .toJson(object);
        return new GsonBuilder()
                .serializeNulls()
                .setPrettyPrinting()
                .create()
                .toJson(new JsonParser().parse(strItem));
    }

}
