package com.example.f23_3175_g12_serenitysoundsapplication.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.f23_3175_g12_serenitysoundsapplication.R;

import java.util.List;

public class FileAdapter extends ArrayAdapter<String> {
    private int selectedPosition = -1; // To keep track of the currently playing song position
    public FileAdapter(Context context, List<String> files) {
        super(context, 0, files);
    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_music, parent, false);
        }

        // Get the file name at the current position
        String fileName = getItem(position);

        // Set the file name to the TextView in the list item
        TextView textViewFileName = convertView.findViewById(R.id.textViewFileName);
        if (fileName != null) {
            textViewFileName.setText(fileName);
            if (position == selectedPosition) {
                // Change the background color or any other styling for the currently playing song
                convertView.setBackgroundColor(getContext().getResources().getColor(R.color.colorPrimary));
            } else {
                // Reset the background for other items
                convertView.setBackgroundColor(getContext().getResources().getColor(android.R.color.transparent));
            }
        }

        return convertView;
    }
}
