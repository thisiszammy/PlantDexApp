package com.zystems.plantdex.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.zystems.plantdex.models.ContributionSubmission;
import com.zystems.plantdex.models.ContributionsManagementResponse;
import com.zystems.plantdex.models.PlantLocation;
import com.zystems.plantdex.models.PlantsManagementResponse;
import com.zystems.plantdex.network.APIService;
import com.zystems.plantdex.network.RetroInstance;

import java.io.IOException;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContributionsManagementResponseViewModel extends ViewModel {
    private MutableLiveData<ContributionsManagementResponse> contributionsManagementResponseMutableLiveData;

    public ContributionsManagementResponseViewModel() {
        this.contributionsManagementResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ContributionsManagementResponse> getContributionsManagementResponseObserver() {
        return contributionsManagementResponseMutableLiveData;
    }

    public void postContributionSubmission(String scientificName, String commonName, String remarks, List<PlantLocation> locations, String filePath){
        APIService apiService = RetroInstance.getRetrofitClient().create(APIService.class);

        Call<ContributionsManagementResponse> contributionsManagementResponseCall = apiService.postContributionSubmission(
                new ContributionSubmission(0, scientificName,commonName, remarks, (new Gson()).toJson(locations), filePath));

        contributionsManagementResponseCall.enqueue(new Callback<ContributionsManagementResponse>() {
            @Override
            public void onResponse(Call<ContributionsManagementResponse> call, Response<ContributionsManagementResponse> response) {
                Log.d(ContributionsManagementResponse.class.getSimpleName(), response.code() + "");
                if(response.code() == 200){
                    contributionsManagementResponseMutableLiveData.postValue(response.body());
                    Log.d(ContributionsManagementResponse.class.getSimpleName(), (new Gson()).toJson(response.body()).toString());
                }else{
                    try {
                        String jsonErrorResponse = response.errorBody().string();
                        Log.d(ContributionsManagementResponse.class.getSimpleName(), jsonErrorResponse + " - error");

                        if(response.errorBody() != null){
                            Gson gson = new Gson();
                            TypeAdapter<ContributionsManagementResponse> adapter = gson.getAdapter(ContributionsManagementResponse.class);
                            contributionsManagementResponseMutableLiveData.postValue(adapter.fromJson(jsonErrorResponse));
                        }else contributionsManagementResponseMutableLiveData.postValue(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ContributionsManagementResponse> call, Throwable t) {
                contributionsManagementResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }
}
