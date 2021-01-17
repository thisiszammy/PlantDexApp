package com.zystems.plantdex.network;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInstance {

    // API Environments
    public static String TEST_ENVIRONMENT = "http://10.0.2.2:44358/api/";
    public static String LIVE_ENVIRONMENT = "https://localhost:44358/api/";
    public static String ACTIVE_ENVIRONMENT = TEST_ENVIRONMENT;

    // API Remote Config Calls
    public static String API_REQUEST_GET_REMOTE_CONFIG = ACTIVE_ENVIRONMENT + "remote-config/version";

    private static Retrofit retrofit;

    public static Retrofit getRetrofitClient(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(ACTIVE_ENVIRONMENT)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
