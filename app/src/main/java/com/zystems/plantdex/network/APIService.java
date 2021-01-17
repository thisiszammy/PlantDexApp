package com.zystems.plantdex.network;

import com.zystems.plantdex.models.ContributionSubmission;
import com.zystems.plantdex.models.ContributionsManagementResponse;
import com.zystems.plantdex.models.PlantsManagementResponse;
import com.zystems.plantdex.models.RemoteConfigResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIService {

    // Remote Config Endpoints
    @GET("remote-config/version")
    Call<RemoteConfigResponse> getRemoteConfig();


    // Plant Management Endpoints
    @GET("plants/search/name")
    Call<PlantsManagementResponse> getPlantByQueryName(@Query("name") String query);


    // Contributions Management Endpoints
    @Headers("Content-Type:application/json")
    @POST("plants/contribute/submit")
    Call<ContributionsManagementResponse> postContributionSubmission(@Body ContributionSubmission contributionSubmission);
}
