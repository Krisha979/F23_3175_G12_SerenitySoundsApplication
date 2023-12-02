package com.example.f23_3175_g12_serenitysoundsapplication.Adapter;

import android.content.Context;
import android.icu.text.DateFormat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.Reminder;
import com.example.f23_3175_g12_serenitysoundsapplication.R;

import java.util.Date;
import java.util.List;

public class ReminderAdapter extends ArrayAdapter<Reminder> {
    public ReminderAdapter(Context context, List<Reminder> reminders) {
        super(context, 0, reminders);
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_reminder, parent, false);
        }

        Reminder reminder = getItem(position);

        TextView titleTextView = convertView.findViewById(R.id.titleTextView);
        TextView timeTextView = convertView.findViewById(R.id.timeTextView);

        titleTextView.setText(reminder.getTitle());

        // Format the time for better readability (you can use SimpleDateFormat)
        String formattedTime = DateFormat.getTimeInstance().format(new Date(reminder.getTimestamp()));
        timeTextView.setText(formattedTime);

        return convertView;
    }
}
