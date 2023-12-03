package com.example.f23_3175_g12_serenitysoundsapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new LocationFragment())
                    .commit();
        }
        toolbar = findViewById(R.id.toolbar);

        // Set up the Toolbar as your transparent action bar
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Customize the toolbar with app name
        TextView appName = findViewById(R.id.app_name);

        // Customize the toolbar with sign-out button
        Button signOutButton = findViewById(R.id.sign_out_button);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Handle sign-out action here (e.g., navigate to login screen)
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();  // Optional: Close the current activity
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }

    private final BottomNavigationView.OnItemSelectedListener navListener =
            new BottomNavigationView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment = null;
                    if (item.getItemId() == R.id.navigation_home) {
                        selectedFragment = new HomeFragment();
                    } else if (item.getItemId() == R.id.navigation_music) {
                        selectedFragment = new MusicFragment();
                    } else if (item.getItemId() == R.id.navigation_location) {
                        selectedFragment = new LocationFragment();
                    } else if (item.getItemId() == R.id.navigation_profile) {
                        selectedFragment = new ScheduleFragment();
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();

                    return true;
                }
            };
}