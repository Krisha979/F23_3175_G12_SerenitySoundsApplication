package com.example.f23_3175_g12_serenitysoundsapplication;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.Reminder;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ReminderDialog extends DialogFragment {
    private EditText titleEditText;
    private EditText descriptionEditText;
    private Button dateButton;
    private Button timeButton;

    private Calendar selectedDateTime = Calendar.getInstance();

    public ReminderDialog() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reminder_dialog, container, false);

        titleEditText = view.findViewById(R.id.titleEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        dateButton = view.findViewById(R.id.dateButton);
        timeButton = view.findViewById(R.id.timeButton);

        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePicker();
            }
        });

        timeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTimePicker();
            }
        });

        Button addButton = view.findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addReminder();
                dismiss();
            }
        });

        return view;

    }
    private void showDatePicker() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                requireContext(),
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        selectedDateTime.set(Calendar.YEAR, year);
                        selectedDateTime.set(Calendar.MONTH, month);
                        selectedDateTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        updateDateButton();
                    }
                },
                selectedDateTime.get(Calendar.YEAR),
                selectedDateTime.get(Calendar.MONTH),
                selectedDateTime.get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
    }

    private void showTimePicker() {
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                requireContext(),
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        selectedDateTime.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        selectedDateTime.set(Calendar.MINUTE, minute);
                        updateTimeButton();
                    }
                },
                selectedDateTime.get(Calendar.HOUR_OF_DAY),
                selectedDateTime.get(Calendar.MINUTE),
                true
        );
        timePickerDialog.show();
    }
    private void updateDateButton() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, MMM d, yyyy", Locale.getDefault());
        dateButton.setText(dateFormat.format(selectedDateTime.getTime()));
    }

    private void updateTimeButton() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a", Locale.getDefault());
        timeButton.setText(timeFormat.format(selectedDateTime.getTime()));
    }
    private void addReminder() {
        // Retrieve user input and add the reminder to the list
        String title = titleEditText.getText().toString().trim();
        String description = descriptionEditText.getText().toString().trim();

        if (!title.isEmpty()) {
            long timestamp = selectedDateTime.getTimeInMillis();
            addReminder(title, description, timestamp);
        }
    }

    private void addReminder(String title, String description, long timestamp) {
        String title1 = titleEditText.getText().toString().trim();
        String description1 = descriptionEditText.getText().toString().trim();

        if (!title.isEmpty()) {
            long timestamp1 = selectedDateTime.getTimeInMillis();
            addReminderToParentFragment(title1, description1, timestamp1);
        }

    }
    private void addReminderToParentFragment(String title, String description, long timestamp) {
        // Add the reminder to the parent fragment (ScheduleFragment)
       // ScheduleFragment parentFragment = (ScheduleFragment) getParentFragmentManager().findFragmentByTag("scheduleFragment");
        Fragment parentFragment = getParentFragmentManager().findFragmentById(R.id.fragment_container); // Replace 'R.id.fragment_container' with the actual ID of your fragment container

        if (parentFragment instanceof ScheduleFragment) {
            ScheduleFragment scheduleFragment = (ScheduleFragment) parentFragment;
            scheduleFragment.addReminder(title, timestamp);
            Toast.makeText(getContext(), "Reminder Added: " + title, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(getContext(), "Parent fragment not found", Toast.LENGTH_SHORT).show();
        }
    }
}