package com.zystems.plantdex.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.zystems.plantdex.ClassifyActivity;
import com.zystems.plantdex.models.ClassifyPlantResponse;
import com.zystems.plantdex.models.ContributionsManagementResponse;
import com.zystems.plantdex.models.UploadedImageFile;
import com.zystems.plantdex.network.APIService;
import com.zystems.plantdex.network.RetroInstance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClassifyPlantResponseViewModel extends ViewModel {
    private MutableLiveData<ClassifyPlantResponse> classifyPlantResponseMutableLiveData;

    public ClassifyPlantResponseViewModel() {
        this.classifyPlantResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<ClassifyPlantResponse> getClassifyPlantResponseObserver(){
        return this.classifyPlantResponseMutableLiveData;
    }

    public void postPlantClassificationRequest(String filePath){
        APIService apiService = RetroInstance.getRetrofitClient().create(APIService.class);

        Call<ClassifyPlantResponse> classifyPlantResponseViewModelCall= apiService.postPlantClassificationRequest(new UploadedImageFile(filePath));

        classifyPlantResponseViewModelCall.enqueue(new Callback<ClassifyPlantResponse>() {
            @Override
            public void onResponse(Call<ClassifyPlantResponse> call, Response<ClassifyPlantResponse> response) {
                Log.d(ClassifyPlantResponseViewModel.class.getSimpleName(), response.code() + "");
                if(response.code() == 200){
                    classifyPlantResponseMutableLiveData.postValue(response.body());
                    Log.d(ClassifyPlantResponseViewModel.class.getSimpleName(), (new Gson()).toJson(response.body()).toString());
                }else{
                    try {
                        String jsonErrorResponse = response.errorBody().string();
                        Log.d(ClassifyPlantResponseViewModel.class.getSimpleName(), jsonErrorResponse + " - error");

                        if(response.errorBody() != null){
                            Gson gson = new Gson();
                            TypeAdapter<ClassifyPlantResponse> adapter = gson.getAdapter(ClassifyPlantResponse.class);
                            classifyPlantResponseMutableLiveData.postValue(adapter.fromJson(jsonErrorResponse));
                        }else classifyPlantResponseMutableLiveData.postValue(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ClassifyPlantResponse> call, Throwable t) {
                classifyPlantResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }
}
