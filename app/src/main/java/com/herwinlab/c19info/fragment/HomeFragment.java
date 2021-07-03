package com.herwinlab.c19info.fragment;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.daasuu.cat.CountAnimationTextView;
import com.github.mikephil.charting.charts.LineChart;
import com.herwinlab.c19info.MainActivity;
import com.herwinlab.c19info.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import www.sanju.motiontoast.MotionToast;

public class HomeFragment extends Fragment {

    public CountAnimationTextView PositiveCase, RecoveredCase, DeathsCase;
    public CountAnimationTextView IndoPosCase, IndoRecCase, IndoDeathCase, IndoCaseToday;
    public TextView LastDataVaccine, TotalVaccine, DateTimeGlobal, DateTimeIdn;
    public String yesterday;

    public ImageView refresh;

    public Button btnDetails;

    public LineChart lineChart;

    Handler handler = new Handler();
    int apiDelayed = 2*1000; // 2second
    Runnable runnable;

    //CustomDialog
    AlertDialog.Builder dialogCustomInd;
    LayoutInflater inflaterInd;
    View dialogViewInd;
    public TextView casesDialog, addCases, recoveredDialog, addRecovered, deathsDialog, addDeaths;
    public TextView activeCases, criticalCases, tracingTest, dateTime, userName;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.home_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PositiveCase =  view.findViewById(R.id.case_positive);
        RecoveredCase = view.findViewById(R.id.case_recovered);
        DeathsCase = view.findViewById(R.id.case_death);

        IndoPosCase = view.findViewById(R.id.case_pos_indo);
        IndoRecCase = view.findViewById(R.id.case_rec_indo);
        IndoDeathCase = view.findViewById(R.id.case_death_indo);
        IndoCaseToday = view.findViewById(R.id.today_case);

        LastDataVaccine = view.findViewById(R.id.last_data);
        TotalVaccine = view.findViewById(R.id.data_vaccine);
        DateTimeGlobal = view.findViewById(R.id.updateglobal);
        DateTimeIdn = view.findViewById(R.id.updateidn);

        btnDetails = view.findViewById(R.id.btnDetail);

        refresh = view.findViewById(R.id.refreshImage);
        userName = view.findViewById(R.id.textUsername);

        Date mydate = new Date(System.currentTimeMillis() - (1000*60*60*24));
        SimpleDateFormat dateFormat = new SimpleDateFormat("M/dd/yy");
        yesterday = dateFormat.format(mydate);

        btnDetails.setOnClickListener(view1 -> customDialogIdn());

        //Volley.newRequestQueue(MainActivity.this).getCache().clear();

        //Fetch Data Json
        fetchDataApi();
        //fetchDataApiVaccine();

        refresh.setOnClickListener(view1 -> {
            Volley.newRequestQueue(getActivity()).getCache().clear();
            fetchDataApi();
            //fetchDataApiVaccine();
            MotionToast.Companion.darkToast(getActivity(),
                    "Completed",
                    "Auto Updated",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(getActivity(), R.font.product_sans_reguler));
        });

        AccountManager accountManager = (AccountManager) getActivity().getSystemService(Context.ACCOUNT_SERVICE);
        Account [] list = accountManager.getAccounts();
        for (Account account:list)
        {
            if(account.type.equalsIgnoreCase("com.google"))
            {
                userName.setText(account.name);
            }
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed( runnable = () -> {
            //Fetch Data Json
            fetchDataApi();
            testData();
            //fetchDataApiVaccine();
           /* MotionToast.Companion.darkToast(getActivity(),
                    "Completed",
                    "Auto Updated",
                    MotionToast.TOAST_SUCCESS,
                    MotionToast.GRAVITY_BOTTOM,
                    MotionToast.LONG_DURATION,
                    ResourcesCompat.getFont(getActivity(), R.font.product_sans_reguler)
            ); */
        }, apiDelayed);
    }

    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    private void fetchDataApi() {
        Volley.newRequestQueue(getActivity()).getCache().clear();

        String UrlGlobalApi = "https://disease.sh/v3/covid-19/all";//"https://coronavirus-19-api.herokuapp.com/all";
        String UrlIndonesiaApi = "https://disease.sh/v3/covid-19/countries/indonesia";//"https://coronavirus-19-api.herokuapp.com/countries/indonesia";
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        StringRequest request = new StringRequest(Request.Method.GET, UrlGlobalApi, response -> {
            try {

                JSONObject jsonObject = new JSONObject(response.toString());
                //Merubah Int ke String trus ke format
                int Positive = jsonObject.getInt("cases");
                int Recovered = jsonObject.getInt("recovered");
                int Deaths = jsonObject.getInt("deaths");
                long date = jsonObject.getLong("updated");

                PositiveCase.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, Positive);
                RecoveredCase.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, Recovered);
                DeathsCase.setDecimalFormat(new DecimalFormat("#,###,###"))
                        .setAnimationDuration(3000)
                        .countAnimation(0, Deaths);

                Date datetime = new java.util.Date(date);
                String datetimeFor = new SimpleDateFormat("dd-MM-yy hh:mm:ss").format(datetime);
                DateTimeGlobal.setText(datetimeFor);

                //String PosFormat = formatter.format(Positive);
                //PositiveCase.setText(PosFormat);

                //PositiveCase.setText(jsonObject.getString("cases"));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);

        StringRequest request1 = new StringRequest(Request.Method.GET, UrlIndonesiaApi, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                int PositiveIdn = jsonObject.getInt("cases");
                int RecoveredIdn = jsonObject.getInt("recovered");
                int DeathIdn = jsonObject.getInt("deaths");
                int TodayCaseIdn = jsonObject.getInt("todayCases");
                long UpdateIndo = jsonObject.getLong("updated");

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

                Date datetimeIdn = new java.util.Date(UpdateIndo);
                String datetimeForIdn = new SimpleDateFormat("dd-MM-yy hh:mm:ss").format(datetimeIdn);
                DateTimeIdn.setText(datetimeForIdn);

            } catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue1 = Volley.newRequestQueue(getActivity());
        requestQueue1.add(request1);

        String UrlGlobalApiVaccine = "https://disease.sh/v3/covid-19/vaccine/coverage/countries/indonesia";
        //Parse Array JSon
        //DecimalFormat formatter = new DecimalFormat("#,###,###");
        DecimalFormat floatFormat = new DecimalFormat("0.00");
        JsonObjectRequest request3 = new JsonObjectRequest(Request.Method.GET, UrlGlobalApiVaccine, null, response -> {
            try {

                JSONObject jsonObject = new JSONObject(response.toString());
                int vaccine = jsonObject.getJSONObject("timeline").getInt(yesterday);
                //Toast.makeText(this, yesterday, Toast.LENGTH_SHORT).show();

                //Perhitungan persentase penduduk
                float persen = ((float) vaccine / (27020 * 10000))*100;
                //Format Number
                String vaccineFor = formatter.format(vaccine);
                String persenFor = floatFormat.format(persen);

                TotalVaccine.setText(vaccineFor+" ("+persenFor+" %)");
                LastDataVaccine.setText(yesterday);
            }
            catch (JSONException e){
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue3 = Volley.newRequestQueue(getActivity());
        requestQueue3.add(request3);
    }

    private void customDialogIdn() {
        dialogCustomInd = new AlertDialog.Builder(getActivity());
        inflaterInd = getLayoutInflater();
        dialogViewInd = inflaterInd.inflate(R.layout.customdialogindonesia, null);
        dialogCustomInd.setView(dialogViewInd);
        dialogCustomInd.setCancelable(true);
        dialogCustomInd.setIcon(R.mipmap.ic_launcher_round);
        dialogCustomInd.setTitle("Covid-19 Cases Details");

        dataDetails();

        dialogCustomInd.setPositiveButton("Exit", (dialog, which)->{

        });

        dialogCustomInd.show();
    }

    private void dataDetails() {
        /****************************Dialog Custom ***********************************/
        casesDialog = dialogViewInd.findViewById(R.id.dialogCases);
        addCases = dialogViewInd.findViewById(R.id.addCases);
        recoveredDialog = dialogViewInd.findViewById(R.id.dialogRecovered);
        addRecovered = dialogViewInd.findViewById(R.id.addRecovered);
        deathsDialog = dialogViewInd.findViewById(R.id.dialogDeaths);
        addDeaths = dialogViewInd.findViewById(R.id.addDeaths);
        activeCases = dialogViewInd.findViewById(R.id.activeCases);
        tracingTest = dialogViewInd.findViewById(R.id.tracingTest);
        dateTime = dialogViewInd.findViewById(R.id.tanggalDialog);

        lineChart = dialogViewInd.findViewById(R.id.chartCase);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);

        String UrlIndonesiaApi = "https://disease.sh/v3/covid-19/countries/indonesia";
        DecimalFormat formatter = new DecimalFormat("#,###,###");
        StringRequest request = new StringRequest(Request.Method.GET, UrlIndonesiaApi, response -> {
            try {

                JSONObject jsonObject = new JSONObject(response.toString());
                //Merubah Int ke String trus ke format
                int Positive = jsonObject.getInt("cases");
                int Recovered = jsonObject.getInt("recovered");
                int Deaths = jsonObject.getInt("deaths");
                int addCase = jsonObject.getInt("todayCases");
                int addreco = jsonObject.getInt("todayRecovered");
                int adddeath = jsonObject.getInt("todayDeaths");
                int active = jsonObject.getInt("active");
                int critical = jsonObject.getInt("critical");
                int test = jsonObject.getInt("tests");
                long datedialog = jsonObject.getLong("updated");

                String PosFormat = formatter.format(Positive);
                casesDialog.setText(PosFormat);
                String addCaseFor = formatter.format(addCase);
                addCases.setText("+ "+addCaseFor);

                String RecFormat = formatter.format(Recovered);
                recoveredDialog.setText(RecFormat);
                String addrecoFor = formatter.format(addreco);
                addRecovered.setText("+ "+addrecoFor);

                String DeaFormat = formatter.format(Deaths);
                deathsDialog.setText(DeaFormat);
                String adddeathFor = formatter.format(adddeath);
                addDeaths.setText("+ "+adddeathFor);

                String activeFor = formatter.format(active);
                String criticalFor = formatter.format(critical);
                String testFor = formatter.format(test);

                activeCases.setText(activeFor);
                //criticalCases.setText(criticalFor);
                tracingTest.setText(testFor);

                Date datetimeDia = new java.util.Date(datedialog);
                String datetimeForDia = new SimpleDateFormat("dd-MM-yy hh:mm:ss").format(datetimeDia);
                dateTime.setText(datetimeForDia);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show());
        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(request);
    }

    /*private void testData() {
        String UrlChartCase = "https://disease.sh/v3/covid-19/historical/indonesia?lastdays=all";
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, UrlChartCase, null, response -> {
            try {
                JSONObject jsonObject = new JSONObject(response.toString());
                int data = jsonObject.getJSONObject("timeline").getJSONObject("cases").getInt("10/7/20");
                TextView textView = findViewById(R.id.tes);
                String intString = Integer.toString(data);
                textView.setText(intString);


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(this, error.getMessage(), Toast.LENGTH_SHORT).show());
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    } */

    private void testData(){
        List<Date> dates = new ArrayList<Date>();

        String start_date ="1/22/20";
        String end_date ="6/20/21";

        DateFormat formatter ;

        formatter = new SimpleDateFormat("M/dd/yy");
        Date  startDate = null;
        Date  endDate = null;
        try {
            startDate = formatter.parse(start_date);
            endDate = formatter.parse(end_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        long interval = 24*1000 * 60 * 60;
        long endTime =endDate.getTime() ;
        long curTime = startDate.getTime();
        while (curTime <= endTime) {
            dates.add(new Date(curTime));
            curTime += interval;
        }
        for(int i=0;i<dates.size();i++){
            String result = formatter.format(dates.get(i));
            TextView textView = getView().findViewById(R.id.tes);
            //textView.append(""+result+"\n");
        }
    }
 }



