package com.example.f23_3175_g12_serenitysoundsapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openLoginPage(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void openCreateAccountPage(View view){
        Intent intent = new Intent(this, CreateAccountActivity.class);
        startActivity(intent);
    }
    public void openMediaPlayerPage(View view){
        Intent intent = new Intent(this, MediaPlayerActivity.class);
        startActivity(intent);
    }

    public void openSchedulePage(View view){
        Intent intent = new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }


}