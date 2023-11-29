package com.example.f23_3175_g12_serenitysoundsapplication.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.MeditationTypes;
import com.example.f23_3175_g12_serenitysoundsapplication.R;

import java.util.List;

public class DashboardAdapter extends BaseAdapter {
    List<MeditationTypes> adapterGalleryImages;

    public DashboardAdapter(List<MeditationTypes> adapterGalleryImages) {
        this.adapterGalleryImages = adapterGalleryImages;
    }

    @Override
    public int getCount() {
        return adapterGalleryImages.size();
    }

    @Override
    public Object getItem(int i) {
        return adapterGalleryImages.get(i);
    }

    @Override
    public long getItemId(int i) {
        return adapterGalleryImages.get(i).getImgId();
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            // Inflate the layout for each grid item
            LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
            view = inflater.inflate(R.layout.grid_item_layout, viewGroup, false);
        }

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView = view.findViewById(R.id.textView);

        // Set the image and text based on the data
        MeditationTypes meditationType = adapterGalleryImages.get(i);
        imageView.setImageResource(meditationType.getImgPic());
        textView.setText(meditationType.getImgName());

        return view;
    }
}
