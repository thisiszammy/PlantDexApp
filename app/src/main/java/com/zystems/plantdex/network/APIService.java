package com.zystems.plantdex.network;

import com.zystems.plantdex.models.RemoteConfigResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface APIService {

    @GET("remote-config/version")
    Call<RemoteConfigResponse> getRemoteConfig();

}
