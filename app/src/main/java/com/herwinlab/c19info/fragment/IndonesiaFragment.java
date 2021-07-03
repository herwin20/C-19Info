package com.herwinlab.c19info.fragment;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.daasuu.cat.CountAnimationTextView;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.herwinlab.c19info.R;
import com.herwinlab.c19info.model.IndonesiaModel;
import com.herwinlab.c19info.viewmodel.IndonesiaViewModel;
import com.herwinlab.c19info.viewmodel.WorldCasesViewModel;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class IndonesiaFragment extends Fragment {

    private ProgressDialog mProgressApp;
    private CountAnimationTextView todayCasesIdn, todayRecoveredIdn, todayDeathsIdn, todayActiveIdn;
    private TextView lastUpdateIdn;

    public IndonesiaFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.indonesia_fragment, container, false);
    }

    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mProgressApp = new ProgressDialog(getActivity(), R.style.AppCompatDialog);
        mProgressApp.setCancelable(true);
        mProgressApp.setMessage("Loading Data ...");
        mProgressApp.show();

        todayCasesIdn = view.findViewById(R.id.caseIndToday);
        todayRecoveredIdn = view.findViewById(R.id.recoveredIdn);
        todayDeathsIdn = view.findViewById(R.id.deathsIdn);
        todayActiveIdn = view.findViewById(R.id.activeIdn);
        lastUpdateIdn = view.findViewById(R.id.lastupdatedIdn);

        PieChart pieChart = view.findViewById(R.id.indonesiaSummaryPie);
        IndonesiaViewModel viewModel = new ViewModelProvider(this,
                new ViewModelProvider.NewInstanceFactory()).get(IndonesiaViewModel.class);
        viewModel.setSummaryIndonesia();
        viewModel.getSummaryIndonesia().observe(getViewLifecycleOwner(), (IndonesiaModel indonesiaModel) -> {
            mProgressApp.dismiss();

            int cases = indonesiaModel.getTodayCases();
            todayCasesIdn.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, cases);

            int recovered = indonesiaModel.getTodayRecovered();
            todayRecoveredIdn.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, recovered);

            int deaths = indonesiaModel.getTodayDeaths();
            todayDeathsIdn.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, deaths);

            int active = indonesiaModel.getActive();
            todayActiveIdn.setDecimalFormat(new DecimalFormat("#,###,###"))
                    .setAnimationDuration(3000)
                    .countAnimation(0, active);

            //Convert Unix Time To Format Std
            Date datetime = new Date(indonesiaModel.getUpdated());
            String datetimeFor = new SimpleDateFormat("dd-MM-yy hh:mm:ss").format(datetime);
            lastUpdateIdn.setText(datetimeFor);

            List<PieEntry> entries = new ArrayList<>();
            entries.add(new PieEntry(indonesiaModel.getCases(), getResources().getString(R.string.confirmed)));
            entries.add(new PieEntry(indonesiaModel.getRecovered(), getResources().getString(R.string.recovered)));
            entries.add(new PieEntry(indonesiaModel.getDeaths(), getResources().getString(R.string.deaths)));

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
        });
    }
}
