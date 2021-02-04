package com.mristudio.massnundoa.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;


import com.mristudio.massnundoa.R;
import com.mristudio.massnundoa.adapter.ItemListAdapter;
import com.mristudio.massnundoa.model.DataModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
   private RecyclerView dataViewRechylerView;
   private ArrayList<DataModel> dataModels = new ArrayList<>();
   ItemListAdapter itemListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dataViewRechylerView = findViewById(R.id.dataViewRechylerView);
        final GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplication(),2);
        dataViewRechylerView.setLayoutManager(gridLayoutManager);

        for (int i=0;i<20;i++){
            DataModel dataModel=new DataModel("","","","");
            dataModels.add(dataModel);
        }
        itemListAdapter = new ItemListAdapter(this, dataModels);
        itemListAdapter.setHasStableIds(true);
        dataViewRechylerView.setAdapter(itemListAdapter);
    }
}