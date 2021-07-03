package com.herwinlab.c19info.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.herwinlab.c19info.api.ApiEndPoint;
import com.herwinlab.c19info.api.ApiService;
import com.herwinlab.c19info.model.IndonesiaModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class IndonesiaViewModel extends ViewModel {

    private MutableLiveData<IndonesiaModel> mutableLiveData = new MutableLiveData<>();

    public void setSummaryIndonesia() {
        Retrofit retrofit = ApiService.getRetrofitService();
        ApiEndPoint apiEndPoint = retrofit.create(ApiEndPoint.class);
        Call<IndonesiaModel> call = apiEndPoint.getSummaryIdn();
        call.enqueue(new Callback<IndonesiaModel>() {
            @Override
            public void onResponse(Call<IndonesiaModel> call, Response<IndonesiaModel> response) {
                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<IndonesiaModel> call, Throwable t) {

            }
        });
    }

    public LiveData<IndonesiaModel> getSummaryIndonesia() { return mutableLiveData;}
}