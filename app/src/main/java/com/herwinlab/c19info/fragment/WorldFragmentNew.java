package com.herwinlab.c19info.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daasuu.cat.CountAnimationTextView;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.gson.JsonObject;
import com.herwinlab.c19info.R;
import com.herwinlab.c19info.adapter.RecyclerViewAdapter;
import com.herwinlab.c19info.api.ApiEndPoint;
import com.herwinlab.c19info.model.RecycleData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import www.sanju.motiontoast.MotionToast;

public class WorldFragmentNew extends Fragment {

    CountAnimationTextView globalCases, globalRecovered, globalDeaths;
    public String limabelas, empatbelas, tigabelas, duabelas, sebelas, sepuluh, sembilan, delapan, tujuh, enam, lima, empat, tiga, dua, satu;
    private LineChart mLineChartGlobal;
    private TextView textView;
    private SearchView searchView;
    private ProgressDialog mProgressApp;

    //Recycle View
    private RecyclerView recyclerView;
    private ArrayList<RecycleData> recyclerDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    public WorldFragmentNew() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.world_fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        globalCases = view.findViewById(R.id.global_case);
        globalRecovered = view.findViewById(R.id.global_recov);
        globalDeaths = view.findViewById(R.id.global_deaths);
        textView = view.findViewById(R.id.test);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_global);

        mLineChartGlobal = view.findViewById(R.id.lineChartGlobal);
        searchView = view.findViewById(R.id.search_data);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                ArrayList<RecycleData> dataFilter = new ArrayList<>();
                for (RecycleData data : recyclerDataArrayList) {
                    String country = data.getCountry().toLowerCase();
                    if (country.contains(s)){dataFilter.add(data);}
                }
                recyclerViewAdapter.setFilter(dataFilter);
                return true;
            }
        });

        // initializing our variables.
        recyclerView = view.findViewById(R.id.recyclerGlobal);
        recyclerView.setNestedScrollingEnabled(false);
        // creating new array list.
        recyclerDataArrayList = new ArrayList<>();
        // calling a method to
        // get all the courses.
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                //Load Recycle View After 5s
                getAllCountry();
            }
        }, 5000);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if(swipeRefreshLayout.isRefreshing()) {
                        Volley.newRequestQueue(getActivity()).getCache().clear();
                        getFetchDataGlobal();
                        getGraphApi();
                        getAllCountry();
                        MotionToast.Companion.createColorToast(getActivity(),
                                "Refresh Content",
                                "Successfully !",
                                MotionToast.TOAST_INFO,
                                MotionToast.GRAVITY_BOTTOM,
                                MotionToast.LONG_DURATION,
                                ResourcesCompat.getFont(getActivity(), R.font.baloo2_regular));
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 2000);
            }
        });

        dateVoid();
        getFetchDataGlobal();
        getGraphApi();

    }

    public void dateVoid() {

        //Buat 1 Hari terakhit
        Date mydate1 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*1));
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("M/d/yy");
        satu = dateFormat1.format(mydate1);

        //Buat 2 Hari terakhit
        Date mydate2 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*2));
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("M/d/yy");
        dua = dateFormat2.format(mydate2);

        //Buat 3 Hari terakhit
        Date mydate3 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*3));
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("M/d/yy");
        tiga = dateFormat3.format(mydate3);

        //Buat 4 Hari terakhit
        Date mydate4 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*4));
        SimpleDateFormat dateFormat4 = new SimpleDateFormat("M/d/yy");
        empat = dateFormat4.format(mydate4);

        //Buat 5 Hari terakhit
        Date mydate5 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*5));
        SimpleDateFormat dateFormat5 = new SimpleDateFormat("M/d/yy");
        lima = dateFormat5.format(mydate5);

        //Buat 6 Hari terakhit
        Date mydate6 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*6));
        SimpleDateFormat dateFormat6 = new SimpleDateFormat("M/d/yy");
        enam = dateFormat6.format(mydate6);

        //Buat 7 Hari terakhit
        Date mydate7 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*7));
        SimpleDateFormat dateFormat7 = new SimpleDateFormat("M/d/yy");
        tujuh = dateFormat7.format(mydate7);

        //Buat 8 Hari terakhit
        Date mydate8 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*8));
        SimpleDateFormat dateFormat8 = new SimpleDateFormat("M/d/yy");
        delapan = dateFormat8.format(mydate8);

        //Buat 9 Hari terakhit
        Date mydate9 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*9));
        SimpleDateFormat dateFormat9 = new SimpleDateFormat("M/d/yy");
        sembilan = dateFormat9.format(mydate9);

        //Buat 10 Hari terakhit
        Date mydate10 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*10));
        SimpleDateFormat dateFormat10 = new SimpleDateFormat("M/d/yy");
        sepuluh = dateFormat10.format(mydate10);

        //Buat 11 Hari terakhit
        Date mydate11 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*11));
        SimpleDateFormat dateFormat11 = new SimpleDateFormat("M/d/yy");
        sebelas = dateFormat11.format(mydate11);

        //Buat 12 Hari terakhit
        Date mydate12 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*12));
        SimpleDateFormat dateFormat12 = new SimpleDateFormat("M/d/yy");
        duabelas = dateFormat12.format(mydate12);

        //Buat 13 Hari terakhit
        Date mydate13 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*13));
        SimpleDateFormat dateFormat13 = new SimpleDateFormat("M/d/yy");
        tigabelas = dateFormat13.format(mydate13);

        //Buat 14 Hari terakhit
        Date mydate14 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*14));
        SimpleDateFormat dateFormat14 = new SimpleDateFormat("M/d/yy");
        empatbelas = dateFormat14.format(mydate14);

        //Buat 15 Hari terakhit
        Date mydate15 = new Date(System.currentTimeMillis() - ((1000*60*60*24)*15));
        SimpleDateFormat dateFormat15 = new SimpleDateFormat("M/d/yy");
        limabelas = dateFormat15.format(mydate15);
    }

    public void getFetchDataGlobal() {
        Volley.newRequestQueue(getActivity()).getCache().clear();
        String UrlGlobalApi = "https://disease.sh/v3/covid-19/all";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, UrlGlobalApi, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int globalCASES = jsonObject.getInt("cases");
                int globalRECOV = jsonObject.getInt("recovered");
                int globalDEATH = jsonObject.getInt("deaths");

                globalCases.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, globalCASES);
                globalRecovered.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, globalRECOV);
                globalDeaths.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, globalDEATH);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> MotionToast.Companion.createColorToast(getActivity(),
                "Error",
                "Check Your Internet",
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(getActivity(), R.font.baloo2_regular)));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }

    public void getGraphApi() {
        Volley.newRequestQueue(getActivity()).getCache().clear();
        String UrlGlobalChartHistory = "https://disease.sh/v3/covid-19/historical/all?lastdays=all";

        dateVoid();
        textView.setText("Global Cases Details update at " + satu);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UrlGlobalChartHistory, null, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                JSONObject cases_global = jsonObject.getJSONObject("cases");
                JSONObject cases_recov = jsonObject.getJSONObject("recovered");
                JSONObject cases_death = jsonObject.getJSONObject("deaths");

                int case_15 = cases_global.getInt(limabelas);
                int case_14 = cases_global.getInt(empatbelas);
                int case_13 = cases_global.getInt(tigabelas);
                int case_12 = cases_global.getInt(duabelas);
                int case_11 = cases_global.getInt(sebelas);
                int case_10 = cases_global.getInt(sepuluh);
                int case_9 = cases_global.getInt(sembilan);
                int case_8 = cases_global.getInt(delapan);
                int case_7 = cases_global.getInt(tujuh);
                int case_6 = cases_global.getInt(enam);
                int case_5 = cases_global.getInt(lima);
                int case_4 = cases_global.getInt(empat);
                int case_3 = cases_global.getInt(tiga);
                int case_2 = cases_global.getInt(dua);
                //int case_1 = cases_global.getInt(satu);

                int recov_15 = cases_recov.getInt(limabelas);
                int recov_14 = cases_recov.getInt(empatbelas);
                int recov_13 = cases_recov.getInt(tigabelas);
                int recov_12 = cases_recov.getInt(duabelas);
                int recov_11 = cases_recov.getInt(sebelas);
                int recov_10 = cases_recov.getInt(sepuluh);
                int recov_9 = cases_recov.getInt(sembilan);
                int recov_8 = cases_recov.getInt(delapan);
                int recov_7 = cases_recov.getInt(tujuh);
                int recov_6 = cases_recov.getInt(enam);
                int recov_5 = cases_recov.getInt(lima);
                int recov_4 = cases_recov.getInt(empat);
                int recov_3 = cases_recov.getInt(tiga);
                int recov_2 = cases_recov.getInt(dua);
                //int recov_1 = cases_recov.getInt(satu);

                int death_15 = cases_death.getInt(limabelas);
                int death_14 = cases_death.getInt(empatbelas);
                int death_13 = cases_death.getInt(tigabelas);
                int death_12 = cases_death.getInt(duabelas);
                int death_11 = cases_death.getInt(sebelas);
                int death_10 = cases_death.getInt(sepuluh);
                int death_9 = cases_death.getInt(sembilan);
                int death_8 = cases_death.getInt(delapan);
                int death_7 = cases_death.getInt(tujuh);
                int death_6 = cases_death.getInt(enam);
                int death_5 = cases_death.getInt(lima);
                int death_4 = cases_death.getInt(empat);
                int death_3 = cases_death.getInt(tiga);
                int death_2 = cases_death.getInt(dua);
                //int death_1 = cases_death.getInt(satu);

                //int todaycase0 = case_1 - case_2;
                int todaycase1 = case_2 - case_3;
                int todaycase2 = case_3 - case_4;
                int todaycase3 = case_4 - case_5;
                int todaycase4 = case_5 - case_6;
                int todaycase5 = case_6 - case_7;
                int todaycase6 = case_7 - case_8;
                int todaycase7 = case_8 - case_9;
                int todaycase8 = case_10 - case_11;
                int todaycase9 = case_11 - case_12;
                int todaycase10 = case_12 - case_13;
                int todaycase11 = case_13 - case_14;
                int todaycase12 = case_14 - case_15;

                //int todayrecov0 = recov_1 - recov_2;
                int todayrecov1 = recov_2 - recov_3;
                int todayrecov2 = recov_3 - recov_4;
                int todayrecov3 = recov_4 - recov_5;
                int todayrecov4 = recov_5 - recov_6;
                int todayrecov5 = recov_6 - recov_7;
                int todayrecov6 = recov_7 - recov_8;
                int todayrecov7 = recov_8 - recov_9;
                int todayrecov8 = recov_10 - recov_11;
                int todayrecov9 = recov_11 - recov_12;
                int todayrecov10 = recov_12 - recov_13;
                int todayrecov11 = recov_13 - recov_14;
                int todayrecov12 = recov_14 - recov_15;

                //int todaydeath0 = death_1 - death_2;
                int todaydeath1 = death_2 - death_3;
                int todaydeath2 = death_3 - death_4;
                int todaydeath3 = death_4 - death_5;
                int todaydeath4 = death_5 - death_6;
                int todaydeath5 = death_6 - death_7;
                int todaydeath6 = death_7 - death_8;
                int todaydeath7 = death_8 - death_9;
                int todaydeath8 = death_10 - death_11;
                int todaydeath9 = death_11 - death_12;
                int todaydeath10 = death_12 - death_13;
                int todaydeath11 = death_13 - death_14;
                int todaydeath12 = death_14 - death_15;

                List<Entry> dataCase = new ArrayList<>();
                dataCase.add(new Entry(1, todaycase12));
                dataCase.add(new Entry(2, todaycase11));
                dataCase.add(new Entry(3, todaycase10));
                dataCase.add(new Entry(4, todaycase9));
                dataCase.add(new Entry(5, todaycase8));
                dataCase.add(new Entry(6, todaycase7));
                dataCase.add(new Entry(7, todaycase6));
                dataCase.add(new Entry(8, todaycase5));
                dataCase.add(new Entry(9, todaycase4));
                dataCase.add(new Entry(10, todaycase3));
                dataCase.add(new Entry(11, todaycase2));
                dataCase.add(new Entry(12, todaycase1));
                //dataCase.add(new Entry(13, todaycase0));

                List<Entry> dataRecov = new ArrayList<>();
                dataRecov.add(new Entry(1, todayrecov12));
                dataRecov.add(new Entry(2, todayrecov11));
                dataRecov.add(new Entry(3, todayrecov10));
                dataRecov.add(new Entry(4, todayrecov9));
                dataRecov.add(new Entry(5, todayrecov8));
                dataRecov.add(new Entry(6, todayrecov7));
                dataRecov.add(new Entry(7, todayrecov6));
                dataRecov.add(new Entry(8, todayrecov5));
                dataRecov.add(new Entry(9, todayrecov4));
                dataRecov.add(new Entry(10, todayrecov3));
                dataRecov.add(new Entry(11, todayrecov2));
                dataRecov.add(new Entry(12, todayrecov1));
                //dataRecov.add(new Entry(13, todayrecov0));

                List<Entry> dataDeath = new ArrayList<>();
                dataDeath.add(new Entry(1, todaydeath12));
                dataDeath.add(new Entry(2, todaydeath11));
                dataDeath.add(new Entry(3, todaydeath10));
                dataDeath.add(new Entry(4, todaydeath9));
                dataDeath.add(new Entry(5, todaydeath8));
                dataDeath.add(new Entry(6, todaydeath7));
                dataDeath.add(new Entry(7, todaydeath6));
                dataDeath.add(new Entry(8, todaydeath5));
                dataDeath.add(new Entry(9, todaydeath4));
                dataDeath.add(new Entry(10, todaydeath3));
                dataDeath.add(new Entry(11, todaydeath2));
                dataDeath.add(new Entry(12, todaydeath1));
                //dataDeath.add(new Entry(13, todaydeath0));

                // Chart Case
                LineDataSet dataSet = new LineDataSet(dataCase, "Case per Day");
                dataSet.setColor(Color.rgb(255, 179, 0));
                dataSet.setValueTextColor(Color.WHITE);
                dataSet.setValueTextSize(8f);
                dataSet.setDrawFilled(true); // Untuk Filled Graph
                dataSet.setFillColor(Color.rgb(255, 179, 0));
                dataSet.setDrawCircles(false); // hilangkan hole
                dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                LineDataSet dataSet2 = new LineDataSet(dataRecov, "Recovery per Day");
                dataSet2.setColor(Color.GREEN);
                dataSet2.setValueTextColor(Color.WHITE);
                dataSet2.setValueTextSize(8f);
                dataSet2.setDrawFilled(true); // Untuk Filled Graph
                dataSet2.setFillColor(Color.GREEN);
                dataSet2.setDrawCircles(false); // hilangkan hole
                dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                LineDataSet dataSet3 = new LineDataSet(dataDeath, "Deaths per Day");
                dataSet3.setColor(Color.RED);
                dataSet3.setValueTextColor(Color.WHITE);
                dataSet3.setValueTextSize(8f);
                dataSet3.setDrawFilled(true); // Untuk Filled Graph
                dataSet3.setFillColor(Color.RED);
                dataSet3.setDrawCircles(false); // hilangkan hole
                dataSet3.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                // Membuat Line data yang akan di set ke Chart
                LineData lineData = new LineData(dataSet, dataSet2, dataSet3);

                // Pengaturan sumbu X
                XAxis xAxis = mLineChartGlobal.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM.BOTTOM);
                xAxis.setCenterAxisLabels(true);
                xAxis.setTextColor(Color.WHITE);

                Legend legend = mLineChartGlobal.getLegend();
                legend.setTextColor(Color.rgb(255, 255, 255));
                legend.setTextSize(11);
                legend.setForm(Legend.LegendForm.CIRCLE);

                // Agar ketika di zoom tidak menjadi pecahan
                xAxis.setGranularity(1f);

                //Menghilangkan sumbu Y yang ada di sebelah kanan
                mLineChartGlobal.getAxisRight().setEnabled(false);
                mLineChartGlobal.getAxisLeft().setTextColor(Color.WHITE);
                mLineChartGlobal.getAxisLeft().setDrawGridLines(false);
                mLineChartGlobal.getXAxis().setDrawGridLines(false);

                // Menghilankan deskripsi pada Chart
                mLineChartGlobal.getDescription().setEnabled(false);

                // Set data ke Chart
                // Tambahkan invalidate setiap kali mengubah data chart
                mLineChartGlobal.setData(lineData);
                mLineChartGlobal.animateXY(3000, 3000);
                mLineChartGlobal.setDragEnabled(true);
                mLineChartGlobal.invalidate();


            }
            catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> MotionToast.Companion.createColorToast(getActivity(),
                "Error",
                "Check Your Internet",
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(getActivity(), R.font.baloo2_regular)));
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonObjectRequest);
    }

    private void getAllCountry() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://disease.sh/v3/covid-19/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndPoint retrofitApi = retrofit.create(ApiEndPoint.class);
        Call<ArrayList<RecycleData>> call = retrofitApi.getAllCountry();

        call.enqueue(new Callback<ArrayList<RecycleData>>() {
            @Override
            public void onResponse(Call<ArrayList<RecycleData>> call, Response<ArrayList<RecycleData>> response) {
                if (response.isSuccessful()) {
                    recyclerDataArrayList = response.body();

                    for (int i = 0; i <  recyclerDataArrayList.size(); i++) {

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);

                        recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, getActivity());
                        recyclerView.setAdapter(recyclerViewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RecycleData>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to Load", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
