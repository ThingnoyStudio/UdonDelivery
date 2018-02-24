package com.thingnoy.thingnoy500v3.manager.http;

import android.icu.text.DateFormat;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.thingnoy.thingnoy500v3.util.DateDeserializer;
//import com.thingnoy.thingnoy500v3.manager.http.ApiService;

import java.util.Date;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by HBO on 16/9/2560.
 */

public class HttpManager {
    private ApiService service;
    private static HttpManager instance;

    public static HttpManager getInstance(){
        if (instance == null){
            instance = new HttpManager();
        }
        return instance;
    }
    private HttpManager(){

        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new CustomHttpLogging());// set Pretty Json form
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build();


        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd")
////                .setDateFormat(DateFormat.SHORT)
//
                .create();


        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl("http://nuuneoi.com/courses/500px/")
                .baseUrl("http://udonfooddelivery.xyz/backend/")
                .client(okHttpClient)//show log response
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(ApiService.class);
    }
    public ApiService getService(){
        return service;
    }

}
