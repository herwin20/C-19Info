package com.herwinlab.c19info.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.herwinlab.c19info.R;
import com.herwinlab.c19info.adapter.ProvinsiViewAdapter;
import com.herwinlab.c19info.adapter.RecyclerViewAdapter;
import com.herwinlab.c19info.api.ApiEndPoint;
import com.herwinlab.c19info.model.Penambahan;
import com.herwinlab.c19info.model.ProvinsiModel;
import com.herwinlab.c19info.model.RecycleData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttp;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import www.sanju.motiontoast.MotionToast;

public class IndonesiaFragmentNew extends Fragment {

    private LinearLayoutManager linearLayoutManager;
    private DividerItemDecoration dividerItemDecoration;
    private List<ProvinsiModel> provinsiList;
    private RecyclerView.Adapter adapter;
    //private RecyclerView recyclerView;
    private ProgressDialog mProgressApp;
    private ImageView imageViewRefresh;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;
    private ProvinsiViewAdapter provinsiViewAdapter;

    //Recycle View
    private RecyclerView recyclerView;
    private ArrayList<ProvinsiModel> provinsiModelArrayList;
    private ProvinsiViewAdapter recyclerViewAdapter;

    public IndonesiaFragmentNew() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.indonesia_fragment_new, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recyclerIndonesia);
        provinsiList = new ArrayList<>();
        adapter = new ProvinsiViewAdapter(getActivity(), provinsiList);

        linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(), linearLayoutManager.getOrientation());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // initializing our variables.
        //recyclerView = view.findViewById(R.id.recyclerGlobal);
        recyclerView.setNestedScrollingEnabled(false);
        // creating new array list.
        provinsiModelArrayList = new ArrayList<>();
        // calling a method to
        // get all the courses.

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if(swipeRefreshLayout.isRefreshing()) {
                        //Volley.newRequestQueue(getActivity()).getCache().clear();
                        getDataProvinsi();
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

        mProgressApp = new ProgressDialog(getActivity(), R.style.AppCompatDialog);
        mProgressApp.setCancelable(true);
        mProgressApp.setMessage("Loading Data ...");
        mProgressApp.show();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            //Load Recycle View After 2s
            getDataProvinsi();
            mProgressApp.dismiss();
        }, 2000);

    }

    private void getDataProvinsi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://apicovid19indonesia-v2.vercel.app/api/indonesia/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndPoint retrofitApi = retrofit.create(ApiEndPoint.class);
        Call<ArrayList<ProvinsiModel>> call = retrofitApi.getAllProvince();

        call.enqueue(new Callback<ArrayList<ProvinsiModel>>() {
            @Override
            public void onResponse(Call<ArrayList<ProvinsiModel>> call, Response<ArrayList<ProvinsiModel>> response) {
                if (response.isSuccessful()) {
                    provinsiModelArrayList = response.body();

                    for (int i = 0; i <  provinsiModelArrayList.size(); i++) {

                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);

                        provinsiViewAdapter = new ProvinsiViewAdapter(getActivity(), provinsiModelArrayList);
                        recyclerView.setAdapter(provinsiViewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ProvinsiModel>> call, Throwable t) {
                Toast.makeText(getActivity(), "Failed to Load", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void getData() {
        Volley.newRequestQueue(getActivity()).getCache().clear();

        String IndonesiaKasusApi = "https://apicovid19indonesia-v2.vercel.app/api/indonesia/provinsi";

        mProgressApp = new ProgressDialog(getActivity(), R.style.AppCompatDialog);
        mProgressApp.setCancelable(true);
        mProgressApp.setMessage("Loading Data ...");
        mProgressApp.show();

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, IndonesiaKasusApi, null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        JSONObject tambahKasus = jsonObject.getJSONObject("penambahan");
                        ProvinsiModel provinsiModel = new ProvinsiModel();
                        provinsiModel.setProvinsi(jsonObject.getString("provinsi"));
                        provinsiModel.setKasus(jsonObject.getInt("kasus"));
                        provinsiModel.setSembuh(jsonObject.getInt("sembuh"));
                        provinsiModel.setMeninggal(jsonObject.getInt("meninggal"));
                        provinsiModel.setDirawat(jsonObject.getInt("dirawat"));



                        provinsiList.add(provinsiModel);
                    }
                }
                catch (JSONException e){
                    e.printStackTrace();
                    mProgressApp.dismiss();
                }
                adapter.notifyDataSetChanged();
                mProgressApp.dismiss();
            }
        }, error -> MotionToast.Companion.createColorToast(getActivity(),
                "Error",
                "Check Your Internet",
                MotionToast.TOAST_ERROR,
                MotionToast.GRAVITY_BOTTOM,
                MotionToast.LONG_DURATION,
                ResourcesCompat.getFont(getActivity(), R.font.baloo2_regular)));

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(jsonArrayRequest);
    } */
}
