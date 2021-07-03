package com.herwinlab.c19info.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.herwinlab.c19info.R;
import com.herwinlab.c19info.model.RecycleData;
import com.herwinlab.c19info.model.RumahSakitModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class RumahSakitViewAdapter extends RecyclerView.Adapter<RumahSakitViewAdapter.RecycleViewHolder> {

    private ArrayList<RumahSakitModel> rumahSakitModelArrayList;
    private Context mContext;
    LayoutInflater layoutInflater;

    public RumahSakitViewAdapter(ArrayList<RumahSakitModel> sakitModelArrayList, Context mContext) {
        this.rumahSakitModelArrayList = sakitModelArrayList;
        this.mContext = mContext;
        layoutInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public RumahSakitViewAdapter.RecycleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rs_per_item, parent, false);
        return new RumahSakitViewAdapter.RecycleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RumahSakitViewAdapter.RecycleViewHolder holder, int position) {

        // Set Data to textView from our modal class.
        RumahSakitModel model = rumahSakitModelArrayList.get(position);

        holder.namaRS.setText(model.getName());
        holder.provinsiRS.setText(model.getProvince());
        holder.alamatRS.setText(model.getAddress());
        holder.regionRS.setText(model.getRegion());
        holder.notelpRS.setText(model.getPhone());

    }

    @Override
    public int getItemCount() {
        return rumahSakitModelArrayList.size();
    }

    public void setFilter(ArrayList<RumahSakitModel> filter) {
        rumahSakitModelArrayList = new ArrayList<>();
        rumahSakitModelArrayList.addAll(filter);
        notifyDataSetChanged();
    }

    public class RecycleViewHolder extends RecyclerView.ViewHolder {

        public TextView namaRS, provinsiRS, alamatRS, regionRS, notelpRS;

        public RecycleViewHolder(@NonNull View itemView) {
            super(itemView);

            namaRS = itemView.findViewById(R.id.nama_rs);
            provinsiRS = itemView.findViewById(R.id.nama_provinsi);
            alamatRS = itemView.findViewById(R.id.address);
            regionRS = itemView.findViewById(R.id.region);
            notelpRS = itemView.findViewById(R.id.no_telp);

        }
    }
}
