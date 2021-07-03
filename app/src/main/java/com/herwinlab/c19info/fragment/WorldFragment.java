package com.herwinlab.c19info.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.daasuu.cat.CountAnimationTextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.herwinlab.c19info.R;
import com.herwinlab.c19info.adapter.RecyclerViewAdapter;
import com.herwinlab.c19info.api.ApiEndPoint;
import com.herwinlab.c19info.model.RecycleData;
import com.herwinlab.c19info.viewmodel.WorldCasesViewModel;

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

public class WorldFragment extends Fragment {

    private ProgressDialog mProgressApp;
    private CountAnimationTextView todayCasesWorld, todayRecoveredWorld, todayDeathsWorld, todayActiveWorld;
    private TextView lastUpdate;

    //Recycle View
    private RecyclerView recyclerView;
    private ArrayList<RecycleData> recyclerDataArrayList;
    private RecyclerViewAdapter recyclerViewAdapter;

    public WorldFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.world_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressApp = new ProgressDialog(getActivity(), R.style.AppCompatDialog);
        mProgressApp.setCancelable(true);
        mProgressApp.setMessage("Loading Data ...");
        mProgressApp.show();

        todayCasesWorld = view.findViewById(R.id.case_today_world);
        todayRecoveredWorld = view.findViewById(R.id.todayrecovered);
        todayDeathsWorld = view.findViewById(R.id.todaydeaths);
        todayActiveWorld = view.findViewById(R.id.activecasestoday);
        lastUpdate = view.findViewById(R.id.lastupdateworld);

        // initializing our variables.
         recyclerView = view.findViewById(R.id.listRecycler);
        // creating new array list.
        recyclerDataArrayList = new ArrayList<>();
        // calling a method to
        // get all the courses.
        getAllCountry();

        PieChart pieChart = view.findViewById(R.id.worldSummaryPie);
        WorldCasesViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(WorldCasesViewModel.class);
        viewModel.setSummaryWorldData();
        viewModel.getSummaryWorldData().observe(getViewLifecycleOwner(), worldCasesModel -> {
            mProgressApp.dismiss();

            int cases = worldCasesModel.getTodayCases();
            todayCasesWorld.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, cases);

            int recovered = worldCasesModel.getTodayRecovered();
            todayRecoveredWorld.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, recovered);

            int deaths = worldCasesModel.getTodayDeaths();
            todayDeathsWorld.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, deaths);

            int active = worldCasesModel.getActive();
            todayActiveWorld.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, active);

            //Convert Unix Time To Format Std
            Date datetime = new Date(worldCasesModel.getUpdated());
            String datetimeFor = new SimpleDateFormat("dd-MM-yy hh:mm:ss").format(datetime);
            lastUpdate.setText(datetimeFor);

            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(worldCasesModel.getCases(), getResources().getString(R.string.confirmed)));
            entries.add(new PieEntry(worldCasesModel.getRecovered(), getResources().getString(R.string.recovered)));
            entries.add(new PieEntry(worldCasesModel.getDeaths(), getResources().getString(R.string.deaths)));

            PieDataSet pieDataSet = new PieDataSet(entries, null); //getResources().getString(R.string.from_corona));
            pieDataSet.setColors(Color.rgb(179, 0, 0), Color.rgb(0, 153, 51), Color.rgb(0, 0, 0) );
            pieDataSet.setValueTextColor(Color.rgb(255, 255, 255));
            pieDataSet.setValueTextSize(10); // Hide Label For Value
            pieDataSet.setSliceSpace(1.5f);

            Description description = new Description();
            description.setText("");//getResources().getString(R.string.last_update) + " : " + datetimeFor);
            description.setTextColor(Color.rgb(59, 59, 59));
            description.setTextSize(6);

            Legend legend = pieChart.getLegend();
            legend.setTextColor(Color.rgb(59,59,59));
            legend.setTextSize(11);
            legend.setForm(Legend.LegendForm.CIRCLE);

            PieData pieData = new PieData(pieDataSet);
            pieChart.setVisibility(View.VISIBLE);
            pieChart.animateXY(3000, 3000);
            pieChart.setDescription(description);
            pieChart.setDrawEntryLabels(false); // Hide Label in Pie
            //pieChart.setHoleColor(Color.TRANSPARENT);
            pieChart.setDrawHoleEnabled(false);
            pieChart.setHoleRadius(70);
            pieChart.setRotationAngle(320);
            pieChart.setData(pieData);

            // Recycle View
            /*adapter = new CasesPerCountryAdapter(getContext());
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(adapter);
            loadListData(); */
        });
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

                        recyclerViewAdapter = new RecyclerViewAdapter(recyclerDataArrayList, getContext());
                        recyclerView.setAdapter(recyclerViewAdapter);

                        mProgressApp.dismiss();
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
