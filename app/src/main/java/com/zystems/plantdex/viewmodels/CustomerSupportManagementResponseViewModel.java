package com.zystems.plantdex.viewmodels;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.zystems.plantdex.models.AppRatingSubmission;
import com.zystems.plantdex.models.ContributionsManagementResponse;
import com.zystems.plantdex.models.CustomerSupportManagementResponse;
import com.zystems.plantdex.models.ReportedComplaintSubmission;
import com.zystems.plantdex.network.APIService;
import com.zystems.plantdex.network.RetroInstance;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CustomerSupportManagementResponseViewModel extends ViewModel {
    private MutableLiveData<CustomerSupportManagementResponse> customerSupportManagementResponseMutableLiveData;

    public CustomerSupportManagementResponseViewModel() {
        this.customerSupportManagementResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<CustomerSupportManagementResponse> getCustomerSupportManagementResponseObserver(){
        return customerSupportManagementResponseMutableLiveData;
    }

    public void postAppRating(String userId, int rating){
        APIService service = RetroInstance.getRetrofitClient().create(APIService.class);
        AppRatingSubmission appRatingSubmission = new AppRatingSubmission(userId, rating);
        Call<CustomerSupportManagementResponse> customerSupportManagementResponseCall = service.postAppRating(appRatingSubmission);
        customerSupportManagementResponseCall.enqueue(new Callback<CustomerSupportManagementResponse>() {
            @Override
            public void onResponse(Call<CustomerSupportManagementResponse> call, Response<CustomerSupportManagementResponse> response) {
                Log.d(CustomerSupportManagementResponse.class.getSimpleName(), response.code() + "");
                if(response.code() == 200){
                    customerSupportManagementResponseMutableLiveData.postValue(response.body());
                    Log.d(CustomerSupportManagementResponse.class.getSimpleName(), (new Gson()).toJson(response.body()).toString());
                }else{
                    try {
                        String jsonErrorResponse = response.errorBody().string();
                        Log.d(CustomerSupportManagementResponse.class.getSimpleName(), jsonErrorResponse + " - error");

                        if(response.errorBody() != null){
                            Gson gson = new Gson();
                            TypeAdapter<CustomerSupportManagementResponse> adapter = gson.getAdapter(CustomerSupportManagementResponse.class);
                            customerSupportManagementResponseMutableLiveData.postValue(adapter.fromJson(jsonErrorResponse));
                        }else customerSupportManagementResponseMutableLiveData.postValue(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerSupportManagementResponse> call, Throwable t) {
                customerSupportManagementResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }

    public void postIssuedComplaint(String userId, String appVersion, String phoneVersion,
                                    String remarks){
        APIService service = RetroInstance.getRetrofitClient().create(APIService.class);
        ReportedComplaintSubmission reportedComplaintSubmission = new ReportedComplaintSubmission(userId, appVersion, phoneVersion, remarks);
        Call<CustomerSupportManagementResponse> customerSupportManagementResponseCall = service.postComplaints(reportedComplaintSubmission);
        customerSupportManagementResponseCall.enqueue(new Callback<CustomerSupportManagementResponse>() {
            @Override
            public void onResponse(Call<CustomerSupportManagementResponse> call, Response<CustomerSupportManagementResponse> response) {
                Log.d(CustomerSupportManagementResponse.class.getSimpleName(), response.code() + "");
                if(response.code() == 200){
                    customerSupportManagementResponseMutableLiveData.postValue(response.body());
                    Log.d(CustomerSupportManagementResponse.class.getSimpleName(), (new Gson()).toJson(response.body()).toString());
                }else{
                    try {
                        String jsonErrorResponse = response.errorBody().string();
                        Log.d(CustomerSupportManagementResponse.class.getSimpleName(), jsonErrorResponse + " - error");

                        if(response.errorBody() != null){
                            Gson gson = new Gson();
                            TypeAdapter<CustomerSupportManagementResponse> adapter = gson.getAdapter(CustomerSupportManagementResponse.class);
                            customerSupportManagementResponseMutableLiveData.postValue(adapter.fromJson(jsonErrorResponse));
                        }else customerSupportManagementResponseMutableLiveData.postValue(null);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<CustomerSupportManagementResponse> call, Throwable t) {
                customerSupportManagementResponseMutableLiveData.postValue(null);
                t.printStackTrace();
            }
        });
    }
}
