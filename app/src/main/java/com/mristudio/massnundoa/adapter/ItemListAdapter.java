package com.mristudio.massnundoa.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mristudio.massnundoa.R;
import com.mristudio.massnundoa.model.DataModel;

import java.util.ArrayList;


public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.HolderItems> {
    Activity activity;
    ArrayList<DataModel> items;

    public ItemListAdapter(Activity activity, ArrayList<DataModel> items) {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public HolderItems onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_data_row, null);
        return new HolderItems(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HolderItems holder, int position) {


    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class HolderItems extends RecyclerView.ViewHolder {


        public HolderItems(@NonNull View itemView) {
            super(itemView);


        }
    }
}
