package com.herwinlab.c19info;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class ProfileApp extends AppCompatActivity {

    ImageView arrowBack;
    CardView instaCard, facebookCard, githubCard;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_layout);

        arrowBack = findViewById(R.id.arrowBack);
        instaCard = findViewById(R.id.instaCard);
        facebookCard = findViewById(R.id.facebookCard);
        githubCard = findViewById(R.id.githubCard);

        arrowBack.setOnClickListener(View ->{
            Intent intent = new Intent(ProfileApp.this, DashboardActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.left_in, R.anim.left_out);
            finish();
        });

        instaCard.setOnClickListener(View ->{
            String url = "https://www.instagram.com/herwinjanuardi/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        facebookCard.setOnClickListener(View ->{
            String url = "https://www.facebook.com/herwin.januardi/";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });

        githubCard.setOnClickListener(View ->{
            String url = "https://github.com/herwin20";
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            startActivity(i);
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition( R.anim.left_in, R.anim.left_out);
        finish();
    }
}
