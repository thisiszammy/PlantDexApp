package com.zystems.plantdex.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.android.gms.common.util.MurmurHash3;
import com.google.gson.Gson;
import com.zystems.plantdex.models.PlantsManagementResponse;
import com.zystems.plantdex.models.RemoteConfigResponse;
import com.zystems.plantdex.network.APIService;
import com.zystems.plantdex.network.RetroInstance;

import org.json.JSONTokener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RemoteConfigResponseViewModel extends ViewModel {

    private MutableLiveData<RemoteConfigResponse> remoteConfigResponseMutableLiveData;

    public RemoteConfigResponseViewModel() {
        remoteConfigResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<RemoteConfigResponse> getRemoteConfigObserver(){
        return remoteConfigResponseMutableLiveData;
    }

    public void getRemoteConfig(){
        APIService apiService = RetroInstance.getRetrofitClient().create(APIService.class);
        Call<RemoteConfigResponse> remoteConfigResponseCall = apiService.getRemoteConfig();
        remoteConfigResponseCall.enqueue(new Callback<RemoteConfigResponse>() {
            @Override
            public void onResponse(Call<RemoteConfigResponse> call, Response<RemoteConfigResponse> response) {
                remoteConfigResponseMutableLiveData.postValue(response.body());
                Log.d(RemoteConfigResponseViewModel.class.getSimpleName(), (new Gson()).toJson(response.body()) + "");
            }

            @Override
            public void onFailure(Call<RemoteConfigResponse> call, Throwable t) {
                remoteConfigResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }
}
