package com.herwinlab.c19info.fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.CircularProgressDrawable;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.herwinlab.c19info.R;
import com.herwinlab.c19info.adapter.RecyclerViewAdapter;
import com.herwinlab.c19info.adapter.RumahSakitViewAdapter;
import com.herwinlab.c19info.api.ApiEndPoint;
import com.herwinlab.c19info.model.RecycleData;
import com.herwinlab.c19info.model.RumahSakitModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import www.sanju.motiontoast.MotionToast;

public class RumahSakitFragment extends Fragment {

    //Recycle View
    private RecyclerView recyclerView;
    private ArrayList<RumahSakitModel> rumahSakitModelArrayList;
    private RumahSakitViewAdapter rumahSakitViewAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView searchView;

    private ProgressDialog mProgressApp;

    public RumahSakitFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.rumahsakit_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh_rs);
        searchView = view.findViewById(R.id.search_rs);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Handler handler = new Handler();
                handler.postDelayed(() -> {
                    if(swipeRefreshLayout.isRefreshing()) {
                        //Volley.newRequestQueue(getActivity()).getCache().clear();
                        getAllRS();
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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                s = s.toLowerCase();
                ArrayList<RumahSakitModel> dataFilter = new ArrayList<>();
                for (RumahSakitModel data : rumahSakitModelArrayList) {
                    String province = data.getProvince().toLowerCase();
                    if(province.contains(s)){dataFilter.add(data);}
                }
                rumahSakitViewAdapter.setFilter(dataFilter);
                return true;
            }
        });

        // initializing our variables.
        recyclerView = view.findViewById(R.id.recyclerRS);
        recyclerView.setNestedScrollingEnabled(false);
        // creating new array list.
        rumahSakitModelArrayList = new ArrayList<>();
        // calling a method to
        // get all the courses.

        mProgressApp = new ProgressDialog(getActivity(), R.style.AppCompatDialog);
        mProgressApp.setCancelable(true);
        mProgressApp.setMessage("Loading Data ...");
        mProgressApp.show();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> {
            //Load Recycle View After 2s
            getAllRS();
        }, 2000);

    }

    private void getAllRS() {
        mProgressApp.dismiss();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dekontaminasi.com/api/id/covid19/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiEndPoint retrofitApi = retrofit.create(ApiEndPoint.class);
        Call<ArrayList<RumahSakitModel>> call = retrofitApi.getAllRS();

        call.enqueue(new Callback<ArrayList<RumahSakitModel>>() {
            @Override
            public void onResponse(Call<ArrayList<RumahSakitModel>> call, Response<ArrayList<RumahSakitModel>> response) {
                if (response.isSuccessful()) {
                    rumahSakitModelArrayList = response.body();

                    for (int i = 0; i < rumahSakitModelArrayList.size(); i++) {
                        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                        recyclerView.setLayoutManager(layoutManager);

                        rumahSakitViewAdapter = new RumahSakitViewAdapter(rumahSakitModelArrayList, getActivity());
                        recyclerView.setAdapter(rumahSakitViewAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<RumahSakitModel>> call, Throwable t) {
                MotionToast.Companion.createColorToast(getActivity(),
                        "Error",
                        "Check Your Internet",
                        MotionToast.TOAST_ERROR,
                        MotionToast.GRAVITY_BOTTOM,
                        MotionToast.LONG_DURATION,
                        ResourcesCompat.getFont(getActivity(), R.font.baloo2_regular));
            }
        });
    }

}
