package com.zystems.plantdex.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.zystems.plantdex.models.ApplicationUser;
import com.zystems.plantdex.models.RemoteConfigResponse;
import com.zystems.plantdex.models.UsersManagementResponse;
import com.zystems.plantdex.network.APIService;
import com.zystems.plantdex.network.RetroInstance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsersManagementResponseViewModel extends ViewModel {
    private MutableLiveData<UsersManagementResponse> usersManagementResponseMutableLiveData;

    public UsersManagementResponseViewModel() {
        this.usersManagementResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<UsersManagementResponse> getUserManagementResponseObserver(){
        return usersManagementResponseMutableLiveData;
    }

    public void postRegistrationForm(String firstName, String middleName, String lastName,
                                     String email, String phoneNumber, String accountType){
        APIService apiService = RetroInstance.getRetrofitClient().create(APIService.class);
        ApplicationUser applicationUser = new ApplicationUser(firstName, middleName, lastName, email,
                phoneNumber, accountType, null, false, null);

        Call<UsersManagementResponse> usersManagementResponseCall = apiService.postRegistrationForm(applicationUser);
        usersManagementResponseCall.enqueue(new Callback<UsersManagementResponse>() {
            @Override
            public void onResponse(Call<UsersManagementResponse> call, Response<UsersManagementResponse> response) {
                Log.d(UsersManagementResponseViewModel.class.getSimpleName(), response.code() + "");
                if(response.code() == 200) {
                    usersManagementResponseMutableLiveData.postValue(response.body());
                    Log.d(UsersManagementResponseViewModel.class.getSimpleName(), (new Gson()).toJson(response.body())+ " - ");
                }
                else{
                    try {
                        String jsonErrorResponse = response.errorBody().string();
                        Log.d(RemoteConfigResponseViewModel.class.getSimpleName(), jsonErrorResponse + " - error");

                        if(response.errorBody() != null){
                            Gson gson = new Gson();
                            TypeAdapter<UsersManagementResponse> adapter = gson.getAdapter(UsersManagementResponse.class);
                            usersManagementResponseMutableLiveData.postValue(adapter.fromJson(jsonErrorResponse));
                        }else usersManagementResponseMutableLiveData.postValue(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

            }

            @Override
            public void onFailure(Call<UsersManagementResponse> call, Throwable t) {
                usersManagementResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }
}
