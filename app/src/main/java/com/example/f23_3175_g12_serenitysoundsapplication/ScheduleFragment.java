package com.example.f23_3175_g12_serenitysoundsapplication;

import static android.content.Context.ALARM_SERVICE;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.room.Room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import com.example.f23_3175_g12_serenitysoundsapplication.Adapter.ReminderAdapter;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.Reminder;
import com.example.f23_3175_g12_serenitysoundsapplication.SerenityDatabase.SerenityDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class ScheduleFragment extends Fragment {
    private List<Reminder> reminderList;
    private ReminderAdapter reminderAdapter;
    SerenityDatabase sdb;

    public ScheduleFragment() {
        // Required empty public constructor
    }
    public static ScheduleFragment newInstance() {
        return new ScheduleFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        // Initialize Room database
        sdb = Room.databaseBuilder(
                        getContext().getApplicationContext(), SerenityDatabase.class, "serenityDatabase.db")
                .build();
        ListView listView = view.findViewById(R.id.listView);
        reminderList = new ArrayList<>();
        reminderAdapter = new ReminderAdapter(requireContext(), reminderList);

        listView.setAdapter(reminderAdapter);
        // Add a sample reminder
        ImageButton addReminderButton = view.findViewById(R.id.addReminderButton);
        addReminderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddReminderDialog();
            }
        });
        addReminder("Sample Reminder", System.currentTimeMillis() + 60000); // 1 minute from now
        ImageButton powerButton = view.findViewById(R.id.powerButton);
        powerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle logout here
                logout();
            }
        });
        return view;
    }
    private void showAddReminderDialog() {
        // Create and show the dialog fragment
        ReminderDialog dialogFragment = new ReminderDialog();
        dialogFragment.show(getParentFragmentManager(), "ReminderDialogFragment");
    }

    public void addReminder(String title, long timeInMillis) {
        Reminder reminder = new Reminder(title, "Test Description", timeInMillis);
        reminderList.add(reminder);
        reminderAdapter.notifyDataSetChanged();

        // Schedule alarm using AlarmManager
        scheduleAlarm(reminder.getTimestamp());

        // Save the reminder to the database
        saveReminderToDatabase(reminder);

    }

    private void scheduleAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(getContext(), AlarmReceiver.class);
        intent.putExtra("title", "Your Reminder Title");
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

        if (alarmManager != null) {
            alarmManager.set(AlarmManager.RTC_WAKEUP, timeInMillis, pendingIntent);
        }
    }

    private void saveReminderToDatabase(Reminder reminder) {
        // Run database operations on a separate thread
        new Thread(new Runnable() {
            @Override
            public void run() {
                sdb.reminderDao().insert(reminder);
            }
        }).start();
    }
    // New method for handling logout
    public void logout() {
        // Implement your logout logic here
        // For example, you can start a new LoginActivity and finish the current activity/fragment
        Intent intent = new Intent(requireContext(), LoginActivity.class);
        startActivity(intent);
        requireActivity().finish(); // Finish the current activity if needed
    }

}