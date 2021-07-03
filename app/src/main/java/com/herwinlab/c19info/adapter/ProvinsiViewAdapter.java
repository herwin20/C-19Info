package com.herwinlab.c19info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.herwinlab.c19info.R;
import com.herwinlab.c19info.model.CountryInfo;
import com.herwinlab.c19info.model.ProvinsiModel;
import com.herwinlab.c19info.model.RecycleData;

import java.security.PublicKey;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ProvinsiViewAdapter extends RecyclerView.Adapter<ProvinsiViewAdapter.ViewHolder>{

    private Context context;
    private static List<ProvinsiModel> provinsiModels;

    public ProvinsiViewAdapter (Context context, List<ProvinsiModel> provinsiModels) {
        this.context = context;
        this.provinsiModels = provinsiModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.province_per_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        ProvinsiModel provinsi = provinsiModels.get(position);

        DecimalFormat decimalFormat = new DecimalFormat("#,###,###");

        holder.provinsi.setText(provinsi.getProvinsi());
        holder.kasusPositif.setText(decimalFormat.format(provinsi.getKasus()));
        holder.kasusSembuh.setText(decimalFormat.format(provinsi.getSembuh()));
        holder.kasusMeniggal.setText(decimalFormat.format(provinsi.getMeninggal()));
        holder.diRawat.setText(decimalFormat.format(provinsi.getDirawat()));

        holder.tambahKasus.setText(decimalFormat.format(provinsi.getPenambahan().getPositif()));
        holder.tambahSembuh.setText(decimalFormat.format(provinsi.getPenambahan().getSembuh()));
        holder.tambahMeni.setText(decimalFormat.format(provinsi.getPenambahan().getMeninggal()));

        holder.kasusPerem.setText(decimalFormat.format(provinsi.getJenisKelamin().getPerempuan()));
        holder.kasusLaki.setText(decimalFormat.format(provinsi.getJenisKelamin().getLakiLaki()));

        holder.nol5.setText(decimalFormat.format(provinsi.getKelompokUmur().get05()));
        holder.enam18.setText(decimalFormat.format(provinsi.getKelompokUmur().get618()));
        holder.sembilanbelas30.setText(decimalFormat.format(provinsi.getKelompokUmur().get1930()));
        holder.tigasatu45.setText(decimalFormat.format(provinsi.getKelompokUmur().get3145()));
        holder.empatenam59.setText(decimalFormat.format(provinsi.getKelompokUmur().get4659()));
        holder.enampuluh.setText(decimalFormat.format(provinsi.getKelompokUmur().get60()));

        holder.lastTime.setText("Update at "+provinsi.getLastDate());
    }

    @Override
    public int getItemCount() {
        return provinsiModels.size();
    }

    public void setFilter(ArrayList<ProvinsiModel> filter) {
        provinsiModels = new ArrayList<>();
        provinsiModels.addAll(filter);
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView kasusPositif, kasusSembuh, kasusMeniggal, provinsi, diRawat;
        public TextView tambahKasus, tambahSembuh, tambahMeni;
        public TextView kasusLaki, kasusPerem, lastTime;
        public TextView nol5, enam18, sembilanbelas30, tigasatu45, empatenam59, enampuluh;

        public ViewHolder(View itemView) {
            super(itemView);

            kasusPositif = itemView.findViewById(R.id.positifIdn);
            kasusSembuh = itemView.findViewById(R.id.sembuhIdn);
            kasusMeniggal = itemView.findViewById(R.id.meninggalIdn);
            provinsi = itemView.findViewById(R.id.provinsiIdn);
            diRawat = itemView.findViewById(R.id.rawatRV);

            tambahKasus = itemView.findViewById(R.id.tambahCase);
            tambahSembuh = itemView.findViewById(R.id.tambahSembuh);
            tambahMeni = itemView.findViewById(R.id.tambahMeni);

            lastTime = itemView.findViewById(R.id.last_time);

            kasusLaki = itemView.findViewById(R.id.manCases);
            kasusPerem = itemView.findViewById(R.id.womenCases);

            nol5 = itemView.findViewById(R.id.nol);
            enam18 = itemView.findViewById(R.id.enam);
            sembilanbelas30 = itemView.findViewById(R.id.sembilanbelas);
            tigasatu45 = itemView.findViewById(R.id.tigasatu);
            empatenam59 = itemView.findViewById(R.id.empatenam);
            enampuluh = itemView.findViewById(R.id.enampuluh);
        }
    }
}
