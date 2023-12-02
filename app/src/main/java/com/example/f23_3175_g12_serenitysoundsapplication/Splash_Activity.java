package com.example.f23_3175_g12_serenitysoundsapplication;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.Reminder;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.User;
import com.example.f23_3175_g12_serenitysoundsapplication.SerenityDatabase.SerenityDatabase;

import java.util.concurrent.Executors;

public class Splash_Activity extends AppCompatActivity {
     SerenityDatabase sdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        // Initialize Room database
        sdb = Room.databaseBuilder(
                        getApplicationContext(), SerenityDatabase.class, "serenityDatabase.db")
                .build();
        // Add a user to the database
        addUserToDatabase();

        new Handler().postDelayed(new Runnable() {


            @Override
            public void run() {
                // This method will be executed once the timer is over
                Intent i = new Intent(Splash_Activity.this, LoginActivity.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
    private void addUserToDatabase() {
        // Create a User object
        User newUser = new User("John Doe", "johndoe123", "johndoe@example.com", "1990-01-01");
// Assuming you have a Reminder object
        Reminder newReminder = new Reminder();
        newReminder.setTitle("Meeting");
        newReminder.setDescription("Discuss project updates");
        newReminder.setTimestamp(System.currentTimeMillis() + 60 * 1000); // Set the reminder time (1 minute from now)


        // Execute the database operation asynchronously
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                // Perform the database operation (insert user)
                sdb.userDao().insert(newUser);
                sdb.reminderDao().insert(newReminder);

            }
        });
    }
}
