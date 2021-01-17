package com.zystems.plantdex.network;

import com.zystems.plantdex.models.PlantsManagementResponse;
import com.zystems.plantdex.models.RemoteConfigResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    // Remote Config Endpoints
    @GET("remote-config/version")
    Call<RemoteConfigResponse> getRemoteConfig();


    // Plant Management Endpoints
    @GET("plants/search/name")
    Call<PlantsManagementResponse> getPlantByQueryName(@Query("name") String query);
}
