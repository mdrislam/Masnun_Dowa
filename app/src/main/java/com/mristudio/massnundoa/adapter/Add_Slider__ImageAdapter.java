package com.mristudio.massnundoa.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mristudio.massnundoa.R;
import com.mristudio.massnundoa.model.ImageModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Add_Slider__ImageAdapter extends RecyclerView.Adapter<Add_Slider__ImageAdapter.OnViewHoalder> {


    private static final String TAG = Add_Slider__ImageAdapter.class.getSimpleName();
    private ArrayList<ImageModel> allimage;
    private static Context mContext;
    private static ImageClick imageClick;


    public Add_Slider__ImageAdapter(ArrayList<ImageModel> allimage, Context mContext) {

        this.allimage = allimage;
        this.mContext = mContext;
        imageClick = (ImageClick) mContext;
    }

    @NonNull
    @Override
    public OnViewHoalder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.add_slider_home_layout, viewGroup, false);

        return new OnViewHoalder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OnViewHoalder hoalder, final int position) {

        Log.e(TAG, "Size  = " + allimage.size());
        if (!allimage.isEmpty()) {
            // Picasso.get().load(allPdfList.get(position).getImageUrl()).resize(150,200).into(hoalder.pdfImageView);

            Picasso.get()
                    .load(allimage.get(position).getImageUrl())
                    .placeholder(R.drawable.logo)
                    .error(R.drawable.logo)
                    .resize(180, 180)
                    .into(hoalder.im_slider);

        }
        //passData

        hoalder.sliderImageClickRL.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {
                imageClick.sliderimageClick(allimage.get(position));
            }
        });
    }


    @Override
    public int getItemCount() {
        return allimage.size();
    }

    public class OnViewHoalder extends RecyclerView.ViewHolder {

        private RelativeLayout sliderImageClickRL;
        private ImageView im_slider;

        public OnViewHoalder(@NonNull View itemView) {
            super(itemView);

            im_slider = itemView.findViewById(R.id.im_slider);
            sliderImageClickRL = itemView.findViewById(R.id.sliderImageClickRL);
        }
    }


   public interface ImageClick {
        void sliderimageClick(ImageModel images);
    }
}
