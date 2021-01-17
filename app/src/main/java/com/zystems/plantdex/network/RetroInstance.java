package com.zystems.plantdex.network;

import android.util.Log;

import com.google.gson.GsonBuilder;
import com.zystems.plantdex.BuildConfig;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    // API Environments
    public static String EMULATOR_ENVIRONMENT = "http://10.0.2.2:44358/api/";
    public static String TEST_ENVIRONMENT = "http://10.0.2.2:44358/api/";
    public static String LIVE_ENVIRONMENT = "https://localhost:44358/api/";
    public static String ACTIVE_ENVIRONMENT = TEST_ENVIRONMENT;

    // API Remote Config Calls
    public static String API_REQUEST_GET_REMOTE_CONFIG = ACTIVE_ENVIRONMENT + "remote-config/version";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + BuildConfig.API_KEY_PDEX)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ACTIVE_ENVIRONMENT)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()))
                    .client(client)
                    .build();
        }
        return retrofit;
    }
}
