package com.herwinlab.c19info;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.herwinlab.c19info.fragment.AboutFragment;
import com.herwinlab.c19info.fragment.HomeFragment;
import com.herwinlab.c19info.fragment.HomeFragmentNew;
import com.herwinlab.c19info.fragment.IndonesiaFragment;
import com.herwinlab.c19info.fragment.IndonesiaFragmentNew;
import com.herwinlab.c19info.fragment.RumahSakitFragment;
import com.herwinlab.c19info.fragment.WorldFragment;
import com.herwinlab.c19info.fragment.WorldFragmentNew;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.Calendar;
import java.util.Date;

public class DashboardActivity extends AppCompatActivity {//implements BottomNavigationView.OnNavigationItemSelectedListener {

    TextView tvToday;
    String hariIni;
    ImageView navBar;

    ChipNavigationBar chipNavigationBar;

    //Handler
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

       /* if (savedInstanceState == null) {
            HomeFragment homeFragment = new HomeFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.flMain, homeFragment)
                    .commit();
        } */

        navBar = findViewById(R.id.navbar);
        navBar.setOnClickListener(view -> {
            Intent intent = new Intent(DashboardActivity.this, ProfileApp.class);
            startActivity(intent);
            overridePendingTransition(R.anim.right_in, R.anim.right_out);
        });

        //tvToday = findViewById(R.id.tvDate);
        Date dateNow = Calendar.getInstance().getTime();
        hariIni = (String) DateFormat.format("EEEE", dateNow);

        //BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        //bottomNavigationView.setOnNavigationItemSelectedListener(this);

        chipNavigationBar = findViewById(R.id.bottomNav);
        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.flMain, new HomeFragmentNew()).commit();

        bottomMenu();

        // Untuk Tanggal
        handler.postDelayed(runnable, 1000);

        //getToday();

    }

    private void bottomMenu() {
        chipNavigationBar.setOnItemSelectedListener
                (i -> {
                    Fragment fragment = null;
                    switch (i){
                        case R.id.world_data:
                            fragment = new WorldFragmentNew();
                            break;

                        case R.id.indonesia_data:
                            fragment = new IndonesiaFragmentNew();

                            break;

                        case R.id.home:
                            fragment = new HomeFragmentNew();

                            break;

                        case R.id.rumahsakit_data:
                            fragment = new RumahSakitFragment();

                            break;

                        case R.id.about:
                            fragment = new AboutFragment();

                            break;
                    }
                    getSupportFragmentManager().beginTransaction()
                            .setCustomAnimations(R.anim.left_in,R.anim.left_out)
                            .replace(R.id.flMain,
                                    fragment).commit();
                });
    }

    //Fungsi Untuk Jam dan Tanggal
    private final Runnable runnable = new Runnable() {
        @Override
        public void run() {
           //getToday();

            handler.postDelayed(this, 1000);
        }
    };

 /*   private void getToday() {
        Date date = Calendar.getInstance().getTime();
        String tanggal = (String) DateFormat.format("d MMMM yyyy hh:mm:ss", date);
        String formatFix = hariIni + ", " + tanggal;
        tvToday.setText(formatFix);
    } */

 /*  @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.world_data:
                WorldFragment worldFragment = new WorldFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, worldFragment)
                        .commit();
                return true;

            case R.id.home:
                HomeFragment homeFragment = new HomeFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, homeFragment)
                        .commit();
                return true;

            case R.id.indonesia_data:
                /*RiwayatFragment riwayatFragment = new RiwayatFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.flMain, riwayatFragment)
                        .commit();
                return true;
        }
        return false;
    } */
}
