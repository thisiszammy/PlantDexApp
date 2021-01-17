package com.zystems.plantdex.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.zystems.plantdex.models.PlantsManagementResponse;
import com.zystems.plantdex.models.RemoteConfigResponse;
import com.zystems.plantdex.network.APIService;
import com.zystems.plantdex.network.RetroInstance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlantsManagementResponseViewModel extends ViewModel {
    private MutableLiveData<PlantsManagementResponse> plantsManagementResponseMutableLiveData;

    public PlantsManagementResponseViewModel() {
        this.plantsManagementResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<PlantsManagementResponse> getPlantsManagementResponseObserver(){
        return plantsManagementResponseMutableLiveData;
    }

    public void getPlantByQueryName(String name){
        APIService apiService = RetroInstance.getRetrofitClient().create(APIService.class);
            Call<PlantsManagementResponse> plantsManagementResponseCall = apiService.getPlantByQueryName(name);
        plantsManagementResponseCall.enqueue(new Callback<PlantsManagementResponse>() {
            @Override
            public void onResponse(Call<PlantsManagementResponse> call, Response<PlantsManagementResponse> response) {
                Log.d(PlantsManagementResponseViewModel.class.getSimpleName(), response.code() + "");
                if(response.code() == 200){
                    plantsManagementResponseMutableLiveData.postValue(response.body());
                    Log.d(PlantsManagementResponseViewModel.class.getSimpleName(), (new Gson()).toJson(response.body()).toString());
                }else{
                    try {
                        String jsonErrorResponse = response.errorBody().string();
                        Log.d(PlantsManagementResponseViewModel.class.getSimpleName(), jsonErrorResponse + " - error");

                        if(response.errorBody() != null){
                            Gson gson = new Gson();
                            TypeAdapter<PlantsManagementResponse> adapter = gson.getAdapter(PlantsManagementResponse.class);
                            plantsManagementResponseMutableLiveData.postValue(adapter.fromJson(jsonErrorResponse));
                        }else plantsManagementResponseMutableLiveData.postValue(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<PlantsManagementResponse> call, Throwable t) {
                plantsManagementResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }
}
