package com.herwinlab.c19info.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herwinlab.c19info.api.ApiEndPoint;
import com.herwinlab.c19info.api.ApiService;
import com.herwinlab.c19info.model.WorldCasesModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class WorldCasesViewModel extends ViewModel {

    private MutableLiveData<WorldCasesModel> mutableLiveData = new MutableLiveData<>();

    public void setSummaryWorldData() {
        Retrofit retrofit = ApiService.getRetrofitService();
        ApiEndPoint apiEndpoint = retrofit.create(ApiEndPoint.class);
        Call<WorldCasesModel> call = apiEndpoint.getSummaryWorld();
        call.enqueue(new Callback<WorldCasesModel>() {
            @Override
            public void onResponse(Call<WorldCasesModel> call, Response<WorldCasesModel> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<WorldCasesModel> call, Throwable t) {

            }
        });

    }

    public LiveData<WorldCasesModel> getSummaryWorldData() {
        return mutableLiveData;
    }
}
