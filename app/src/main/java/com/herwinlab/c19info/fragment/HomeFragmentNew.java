package com.herwinlab.c19info.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daasuu.cat.CountAnimationTextView;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.slider.Slider;
import com.herwinlab.c19info.R;
import com.herwinlab.c19info.adapter.SliderDataAdapter;
import com.herwinlab.c19info.model.SliderDataModel;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import www.sanju.motiontoast.MotionToast;

public class HomeFragmentNew extends Fragment {

    // Urls of our images.
    public String url1 = "https://image.freepik.com/free-vector/flat-vaccine-instagram-post-collection_23-2148989133.jpg";
    public String url2 = "https://image.freepik.com/free-vector/coronavirus-prevention-infographic_52683-38163.jpg";
    public String url3 = "https://image.freepik.com/free-vector/how-wear-face-mask-right-wrong_52683-48770.jpg";
    public String url4 = "https://image.freepik.com/free-vector/people-wearing-medical-mask_52683-35467.jpg";
    public String url5 = "https://image.freepik.com/free-vector/organic-flat-vaccination-campaign_23-2148962139.jpg";
    public String url6 = "https://image.freepik.com/free-vector/hand-drawn-vaccination-campaign-badges_23-2148964541.jpg";

    public CountAnimationTextView IndoPosCase, IndoRecCase, IndoDeathCase, IndoCaseToday, IndoRecToday, IndoDeathsToday;
    ImageView refreshImg;
    private LineChart mLineChart, mLineChartVaccine;
    private String limabelas, empatbelas, tigabelas, duabelas, sebelas, sepuluh, sembilan, delapan, tujuh, enam, lima, empat, tiga, dua, satu;
    private TextView vaccineIdn, lastUpdatedCases, lastUpdatedVaccine;

    public HomeFragmentNew() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<SliderDataModel> sliderDataModelArrayList = new ArrayList<>();
        SliderView sliderView = view.findViewById(R.id.imageSlider);

        // adding the urls inside array list
        sliderDataModelArrayList.add(new SliderDataModel(url1));
        sliderDataModelArrayList.add(new SliderDataModel(url2));
        sliderDataModelArrayList.add(new SliderDataModel(url3));
        sliderDataModelArrayList.add(new SliderDataModel(url4));
        sliderDataModelArrayList.add(new SliderDataModel(url5));
        sliderDataModelArrayList.add(new SliderDataModel(url6));

        // passing this array list inside our adapter class.
        SliderDataAdapter adapter = new SliderDataAdapter(getActivity(), sliderDataModelArrayList);
        // below method is used to set auto cycle direction in left to
        // right direction you can change according to requirement.
        sliderView.setAutoCycleDirection(SliderView.LAYOUT_DIRECTION_LTR);
        // below method is used to
        // setadapter to sliderview.
        sliderView.setSliderAdapter(adapter);
        // below method is use to set
        // scroll time in seconds.
        sliderView.setScrollTimeInSec(5);
        //Get Animation Indicator
        sliderView.setIndicatorAnimation(IndicatorAnimationType.WORM);
        // to set it scrollable automatically
        // we use below method.
        sliderView.setAutoCycle(true);
        // to start autocycle below method is used.
        sliderView.startAutoCycle();

        IndoPosCase = view.findViewById(R.id.case_idn_new);
        IndoRecCase = view.findViewById(R.id.recovered_new_idn);
        IndoDeathCase = view.findViewById(R.id.deaths_new_idn);
        IndoCaseToday = view.findViewById(R.id.add_cases);
        IndoRecToday = view.findViewById(R.id.add_recovered);
        IndoDeathsToday = view.findViewById(R.id.add_deaths);
        vaccineIdn = view.findViewById(R.id.vaccineIdn);
        lastUpdatedCases = view.findViewById(R.id.lastUpdatedCases);
        lastUpdatedVaccine = view.findViewById(R.id.lastUpdatedVaccine);

        mLineChart = view.findViewById(R.id.barCharInd);
        mLineChartVaccine = view.findViewById(R.id.lineCharVaccine);

        dateVoid();

        fetchDataApi();
        fetchDataApiVaccine();

        refreshImg = view.findViewById(R.id.refreshImg);

        refreshImg.setOnClickListener(view1 -> {
            Volley.newRequestQueue(getActivity()).getCache().clear();
            fetchDataApi();
            fetchDataApiVaccine();
            //fetchDataApiVaccine();
            MotionToast.Companion.createColorToast(getActivity(),
                    "Refresh Content",
                    "Successfully !",
                    MotionToast.TOAST_INFO,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(getActivity(), R.font.baloo2_regular));
        });

    }

    private void fetchDataApi() {
        Volley.newRequestQueue(getActivity()).getCache().clear();

        String UrlIndonesiaApi = "https://disease.sh/v3/covid-19/countries/indonesia";

        StringRequest request = new StringRequest(Request.Method.GET, UrlIndonesiaApi, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response);
                int PositiveIdn = jsonObject.getInt("cases");
                int RecoveredIdn = jsonObject.getInt("recovered");
                int DeathIdn = jsonObject.getInt("deaths");
                int TodayCaseIdn = jsonObject.getInt("todayCases");
                int TodayRecIdn = jsonObject.getInt("todayRecovered");
                int TodayDeathsIdn = jsonObject.getInt("todayDeaths");

                IndoPosCase.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, PositiveIdn);
                IndoRecCase.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, RecoveredIdn);
                IndoDeathCase.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, DeathIdn);
                IndoCaseToday.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, TodayCaseIdn);
                IndoRecToday.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, TodayRecIdn);
                IndoDeathsToday.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, TodayDeathsIdn);


                String UrlGlobalApiHistory = "https://disease.sh/v3/covid-19/historical/indonesia";
                //Parse Array JSon
                //DecimalFormat formatter = new DecimalFormat("#,###,###");
                DecimalFormat floatFormat = new DecimalFormat("0.00");
                JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, UrlGlobalApiHistory, null, response1 -> {
                    try {

                        JSONObject jsonObject1 = new JSONObject(response1.toString());
                        JSONObject cases_active = jsonObject1.getJSONObject("timeline").getJSONObject("cases");
                        JSONObject recovereds_active = jsonObject1.getJSONObject("timeline").getJSONObject("recovered");
                        JSONObject deathss_active = jsonObject1.getJSONObject("timeline").getJSONObject("deaths");
                        int case_15 = cases_active.getInt(limabelas);
                        int case_14 = cases_active.getInt(empatbelas);
                        int case_13 = cases_active.getInt(tigabelas);
                        int case_12 = cases_active.getInt(duabelas);
                        int case_11 = cases_active.getInt(sebelas);
                        int case_10 = cases_active.getInt(sepuluh);
                        int case_9 = cases_active.getInt(sembilan);
                        int case_8 = cases_active.getInt(delapan);
                        int case_7 = cases_active.getInt(tujuh);
                        int case_6 = cases_active.getInt(enam);
                        int case_5 = cases_active.getInt(lima);
                        int case_4 = cases_active.getInt(empat);
                        int case_3 = cases_active.getInt(tiga);
                        int case_2 = cases_active.getInt(dua);
                        int case_1 = cases_active.getInt(satu);

                        int todaycase = case_1 - case_2;
                        int todaycase0 = case_2 - case_3;
                        int todaycase1 = case_3 - case_4;
                        int todaycase2 = case_4 - case_5;
                        int todaycase3 = case_5 - case_6;
                        int todaycase4 = case_7 - case_8;
                        int todaycase5 = case_8 - case_9;
                        int todaycase6 = case_9 - case_10;
                        int todaycase7 = case_10 - case_11;
                        int todaycase8 = case_11 - case_12;
                        int todaycase9 = case_12 - case_13;
                        int todaycase10 = case_13 - case_14;
                        int todaycase11 = case_14 - case_15;

                        int recovered_15 = recovereds_active.getInt(limabelas);
                        int recovered_14 = recovereds_active.getInt(empatbelas);
                        int recovered_13 = recovereds_active.getInt(tigabelas);
                        int recovered_12 = recovereds_active.getInt(duabelas);
                        int recovered_11 = recovereds_active.getInt(sebelas);
                        int recovered_10 = recovereds_active.getInt(sepuluh);
                        int recovered_9 = recovereds_active.getInt(sembilan);
                        int recovered_8 = recovereds_active.getInt(delapan);
                        int recovered_7 = recovereds_active.getInt(tujuh);
                        int recovered_6 = recovereds_active.getInt(enam);
                        int recovered_5 = recovereds_active.getInt(lima);
                        int recovered_4 = recovereds_active.getInt(empat);
                        int recovered_3 = recovereds_active.getInt(tiga);
                        int recovered_2 = recovereds_active.getInt(dua);
                        int recovered_1 = recovereds_active.getInt(satu);

                        int todayrecovered = recovered_1 - recovered_2;
                        int todayrecovered0 = recovered_2 - recovered_3;
                        int todayrecovered1 = recovered_3 - recovered_4;
                        int todayrecovered2 = recovered_4 - recovered_5;
                        int todayrecovered3 = recovered_5 - recovered_6;
                        int todayrecovered4 = recovered_7 - recovered_8;
                        int todayrecovered5 = recovered_8 - recovered_9;
                        int todayrecovered6 = recovered_9 - recovered_10;
                        int todayrecovered7 = recovered_10 - recovered_11;
                        int todayrecovered8 = recovered_11 - recovered_12;
                        int todayrecovered9 = recovered_12 - recovered_13;
                        int todayrecovered10 = recovered_13 - recovered_14;
                        int todayrecovered11 = recovered_14 - recovered_15;

                        int deaths_15 = deathss_active.getInt(limabelas);
                        int deaths_14 = deathss_active.getInt(empatbelas);
                        int deaths_13 = deathss_active.getInt(tigabelas);
                        int deaths_12 = deathss_active.getInt(duabelas);
                        int deaths_11 = deathss_active.getInt(sebelas);
                        int deaths_10 = deathss_active.getInt(sepuluh);
                        int deaths_9 = deathss_active.getInt(sembilan);
                        int deaths_8 = deathss_active.getInt(delapan);
                        int deaths_7 = deathss_active.getInt(tujuh);
                        int deaths_6 = deathss_active.getInt(enam);
                        int deaths_5 = deathss_active.getInt(lima);
                        int deaths_4 = deathss_active.getInt(empat);
                        int deaths_3 = deathss_active.getInt(tiga);
                        int deaths_2 = deathss_active.getInt(dua);
                        int deaths_1 = deathss_active.getInt(satu);

                        int todaydeaths = deaths_1 - deaths_2;
                        int todaydeaths0 = deaths_2 - deaths_3;
                        int todaydeaths1 = deaths_3 - deaths_4;
                        int todaydeaths2 = deaths_4 - deaths_5;
                        int todaydeaths3 = deaths_5 - deaths_6;
                        int todaydeaths4 = deaths_7 - deaths_8;
                        int todaydeaths5 = deaths_8 - deaths_9;
                        int todaydeaths6 = deaths_9 - deaths_10;
                        int todaydeaths7 = deaths_10 - deaths_11;
                        int todaydeaths8 = deaths_11 - deaths_12;
                        int todaydeaths9 = deaths_12 - deaths_13;
                        int todaydeaths10 = deaths_13 - deaths_14;
                        int todaydeaths11 = deaths_13 - deaths_15;

                        List<Entry> dataCase = new ArrayList<>();
                        dataCase.add(new Entry(1, todaycase11));
                        dataCase.add(new Entry(2, todaycase10));
                        dataCase.add(new Entry(3, todaycase9));
                        dataCase.add(new Entry(4, todaycase8));
                        dataCase.add(new Entry(5, todaycase7));
                        dataCase.add(new Entry(6, todaycase6));
                        dataCase.add(new Entry(7, todaycase5));
                        dataCase.add(new Entry(8, todaycase4));
                        dataCase.add(new Entry(9, todaycase3));
                        dataCase.add(new Entry(10, todaycase2));
                        dataCase.add(new Entry(11, todaycase1));
                        dataCase.add(new Entry(12, todaycase0));
                        dataCase.add(new Entry(13, todaycase));

                        List<Entry> dataRecovered = new ArrayList<>();
                        dataRecovered.add(new Entry(1, todayrecovered10));
                        dataRecovered.add(new Entry(2, todayrecovered10));
                        dataRecovered.add(new Entry(3, todayrecovered9));
                        dataRecovered.add(new Entry(4, todayrecovered8));
                        dataRecovered.add(new Entry(5, todayrecovered7));
                        dataRecovered.add(new Entry(6, todayrecovered6));
                        dataRecovered.add(new Entry(7, todayrecovered5));
                        dataRecovered.add(new Entry(8, todayrecovered4));
                        dataRecovered.add(new Entry(9, todayrecovered3));
                        dataRecovered.add(new Entry(10, todayrecovered2));
                        dataRecovered.add(new Entry(11, todayrecovered1));
                        dataRecovered.add(new Entry(12, todayrecovered0));
                        dataRecovered.add(new Entry(13, todayrecovered));

                        List<Entry> dataDeaths = new ArrayList<>();
                        dataDeaths.add(new Entry(1, todaydeaths11));
                        dataDeaths.add(new Entry(2, todaydeaths10));
                        dataDeaths.add(new Entry(3, todaydeaths9));
                        dataDeaths.add(new Entry(4, todaydeaths8));
                        dataDeaths.add(new Entry(5, todaydeaths7));
                        dataDeaths.add(new Entry(6, todaydeaths6));
                        dataDeaths.add(new Entry(7, todaydeaths5));
                        dataDeaths.add(new Entry(8, todaydeaths4));
                        dataDeaths.add(new Entry(9, todaydeaths3));
                        dataDeaths.add(new Entry(10, todaydeaths2));
                        dataDeaths.add(new Entry(11, todaydeaths1));
                        dataDeaths.add(new Entry(12, todaydeaths0));
                        dataDeaths.add(new Entry(13, todaydeaths));

                        // Chart Case
                        LineDataSet dataSet = new LineDataSet(dataCase, "Case per Day");
                        dataSet.setColor(Color.rgb(255, 179, 0));
                        dataSet.setValueTextColor(Color.WHITE);
                        dataSet.setValueTextSize(8f);
                        dataSet.setDrawFilled(true); // Untuk Filled Graph
                        dataSet.setFillColor(Color.rgb(255, 179, 0));
                        dataSet.setDrawCircles(false); // hilangkan hole
                        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                        LineDataSet dataSet2 = new LineDataSet(dataRecovered, "Recovery per Day");
                        dataSet2.setColor(Color.GREEN);
                        dataSet2.setValueTextColor(Color.WHITE);
                        dataSet2.setValueTextSize(8f);
                        dataSet2.setDrawFilled(true); // Untuk Filled Graph
                        dataSet2.setFillColor(Color.GREEN);
                        dataSet2.setDrawCircles(false); // hilangkan hole
                        dataSet2.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                        LineDataSet dataSet3 = new LineDataSet(dataDeaths, "Deaths per Day");
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
                        XAxis xAxis = mLineChart.getXAxis();
                        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM.BOTTOM);
                        xAxis.setCenterAxisLabels(true);
                        xAxis.setTextColor(Color.WHITE);

                        Legend legend = mLineChart.getLegend();
                        legend.setTextColor(Color.rgb(255, 255, 255));
                        legend.setTextSize(11);
                        legend.setForm(Legend.LegendForm.CIRCLE);

                        // Agar ketika di zoom tidak menjadi pecahan
                        xAxis.setGranularity(1f);

                        //Menghilangkan sumbu Y yang ada di sebelah kanan
                        mLineChart.getAxisRight().setEnabled(false);
                        mLineChart.getAxisLeft().setTextColor(Color.WHITE);
                        mLineChart.getAxisLeft().setDrawGridLines(false);
                        mLineChart.getXAxis().setDrawGridLines(false);

                        // Menghilankan deskripsi pada Chart
                        mLineChart.getDescription().setEnabled(false);

                        // Set data ke Chart
                        // Tambahkan invalidate setiap kali mengubah data chart
                        mLineChart.setData(lineData);
                        mLineChart.animateXY(3000, 3000);
                        mLineChart.setDragEnabled(true);
                        mLineChart.invalidate();

                        lastUpdatedCases.setText(satu);

                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }
                }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show());
                RequestQueue requestQueue3 = Volley.newRequestQueue(getActivity());
                requestQueue3.add(request3);


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
        requestQueue.add(request);

    }

    public void fetchDataApiVaccine() {
        Volley.newRequestQueue(getActivity()).getCache().clear();
        String UrlGlobalApiVaccine = "https://disease.sh/v3/covid-19/vaccine/coverage/countries/indonesia";
        //Parse Array JSon
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        DecimalFormat floatFormat = new DecimalFormat("0.00");
        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, UrlGlobalApiVaccine, null, response -> {
            try {

                JSONObject jsonObject = new JSONObject(response.toString());
                int vaccine1 = jsonObject.getJSONObject("timeline").getInt(satu);
                int vaccine2 = jsonObject.getJSONObject("timeline").getInt(dua);
                int vaccine3 = jsonObject.getJSONObject("timeline").getInt(tiga);
                int vaccine4 = jsonObject.getJSONObject("timeline").getInt(empat);
                int vaccine5 = jsonObject.getJSONObject("timeline").getInt(lima);
                int vaccine6 = jsonObject.getJSONObject("timeline").getInt(enam);
                int vaccine7 = jsonObject.getJSONObject("timeline").getInt(tujuh);
                int vaccine8 = jsonObject.getJSONObject("timeline").getInt(delapan);
                int vaccine9 = jsonObject.getJSONObject("timeline").getInt(sembilan);
                int vaccine10 = jsonObject.getJSONObject("timeline").getInt(sepuluh);
                int vaccine11 = jsonObject.getJSONObject("timeline").getInt(sebelas);
                int vaccine12 = jsonObject.getJSONObject("timeline").getInt(duabelas);
                int vaccine13 = jsonObject.getJSONObject("timeline").getInt(tigabelas);
                int vaccine14 = jsonObject.getJSONObject("timeline").getInt(empatbelas);

                int todayvaccine = vaccine1 - vaccine2;
                int todayvaccine0 = vaccine2 - vaccine3;
                int todayvaccine1 = vaccine3 - vaccine4;
                int todayvaccine2 = vaccine4 - vaccine5;
                int todayvaccine3 = vaccine5 - vaccine6;
                int todayvaccine4 = vaccine7 - vaccine8;
                int todayvaccine5 = vaccine8 - vaccine9;
                int todayvaccine6 = vaccine9 - vaccine10;
                int todayvaccine7 = vaccine10 - vaccine11;
                int todayvaccine8 = vaccine11 - vaccine12;
                int todayvaccine9 = vaccine12 - vaccine13;
                int todayvaccine10 = vaccine13 - vaccine14;

                //Perhitungan persentase penduduk
                float persen = ((float) vaccine2 / (27020 * 10000))*100;
                //Format Number
                String vaccineFor = formatter.format(vaccine2);
                String persenFor = floatFormat.format(persen);

                vaccineIdn.setText(vaccineFor+" ("+persenFor+" %)");

                List<Entry> datavaccine = new ArrayList<>();
                datavaccine.add(new Entry(1, todayvaccine10));
                datavaccine.add(new Entry(2, todayvaccine9));
                datavaccine.add(new Entry(3, todayvaccine8));
                datavaccine.add(new Entry(4, todayvaccine7));
                datavaccine.add(new Entry(5, todayvaccine6));
                datavaccine.add(new Entry(6, todayvaccine5));
                datavaccine.add(new Entry(7, todayvaccine4));
                datavaccine.add(new Entry(8, todayvaccine3));
                datavaccine.add(new Entry(9, todayvaccine2));
                datavaccine.add(new Entry(10, todayvaccine1));
                datavaccine.add(new Entry(11, todayvaccine0));
                datavaccine.add(new Entry(11, todayvaccine));

                // Chart Case
                LineDataSet dataSet = new LineDataSet(datavaccine, "Vaccine per Day");
                dataSet.setColor(Color.GREEN);
                dataSet.setValueTextColor(Color.WHITE);
                dataSet.setValueTextSize(8f);
                dataSet.setDrawFilled(true); // Untuk Filled Graph
                dataSet.setFillColor(Color.GREEN);
                dataSet.setDrawCircles(false); // hilangkan hole
                dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

                // Membuat Line data yang akan di set ke Chart
                LineData lineData = new LineData(dataSet);

                // Pengaturan sumbu X
                XAxis xAxis = mLineChartVaccine.getXAxis();
                xAxis.setPosition(XAxis.XAxisPosition.BOTTOM.BOTTOM);
                xAxis.setCenterAxisLabels(true);
                xAxis.setTextColor(Color.WHITE);

                Legend legend = mLineChartVaccine.getLegend();
                legend.setTextColor(Color.rgb(255, 255, 255));
                legend.setTextSize(11);
                legend.setForm(Legend.LegendForm.CIRCLE);

                // Agar ketika di zoom tidak menjadi pecahan
                xAxis.setGranularity(1f);

                //Menghilangkan sumbu Y yang ada di sebelah kanan
                mLineChartVaccine.getAxisRight().setEnabled(false);
                mLineChartVaccine.getAxisLeft().setTextColor(Color.WHITE);
                mLineChartVaccine.getAxisLeft().setDrawGridLines(false);
                mLineChartVaccine.getXAxis().setDrawGridLines(false);

                // Menghilankan deskripsi pada Chart
                mLineChartVaccine.getDescription().setEnabled(false);

                // Set data ke Chart
                // Tambahkan invalidate setiap kali mengubah data chart
                mLineChartVaccine.setData(lineData);
                mLineChartVaccine.animateXY(3000, 3000);
                mLineChartVaccine.setDragEnabled(true);
                mLineChartVaccine.invalidate();

                lastUpdatedVaccine.setText(satu);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue3 = Volley.newRequestQueue(getActivity());
        requestQueue3.add(request3);

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
}
