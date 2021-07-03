package com.herwinlab.c19info.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.herwinlab.c19info.R;

public class AboutFragment extends Fragment {

    TextView diseaseSitus, mathdroSitus, kawalSitus;

    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.about_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        diseaseSitus = view.findViewById(R.id.click_disease);
        mathdroSitus = view.findViewById(R.id.click_mathdro);
        kawalSitus = view.findViewById(R.id.click_kawal);

        diseaseSitus.setOnClickListener(view1 -> {
            String url = "https://disease.sh/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        mathdroSitus.setOnClickListener(view1 -> {
            String url = "https://indonesia-covid-19.mathdro.id/api/provinsi";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        kawalSitus.setOnClickListener(view1 -> {
            String url = "https://apicovid19indonesia-v2.vercel.app/api";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

    }

}
