package com.herwinlab.c19info.api;

import com.herwinlab.c19info.model.IndonesiaModel;
import com.herwinlab.c19info.model.ProvinsiModel;
import com.herwinlab.c19info.model.RecycleData;
import com.herwinlab.c19info.model.RumahSakitModel;
import com.herwinlab.c19info.model.WorldCasesModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiEndPoint {

    @GET("countries")
    Call<ArrayList<RecycleData>> getAllCountry();

    @GET("provinsi/more")
    Call<ArrayList<ProvinsiModel>> getAllProvince();

    @GET("hospitals")
    Call<ArrayList<RumahSakitModel>> getAllRS();

    //@GET(Api.END_POINT_WORLD_HISTORY_2)
    //Call<List<RiwayatModel>> getHistoryList(@Path("date") String date);

    @GET(Api.END_POINT_SUMMARY_WORLD)
    Call<WorldCasesModel> getSummaryWorld();

    @GET(Api.END_POINT_IDN)
    Call<IndonesiaModel> getSummaryIdn();
}
