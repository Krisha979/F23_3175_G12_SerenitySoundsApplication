package com.example.f23_3175_g12_serenitysoundsapplication;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.User;
import com.example.f23_3175_g12_serenitysoundsapplication.SerenityDatabase.SerenityDatabase;
import com.example.f23_3175_g12_serenitysoundsapplication.databinding.ActivityLoginBinding;

import java.util.concurrent.Executors;

public class LoginActivity extends AppCompatActivity {
    ActivityLoginBinding binding;
    SerenityDatabase sdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
       // setContentView(R.layout.activity_login);
        setContentView(binding.getRoot());

        sdb = Room.databaseBuilder(
                        getApplicationContext(), SerenityDatabase.class, "serenityDatabase.db")
                .build();
        binding.btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.txtusername.getText().toString();
                String password = binding.txtpassword.getText().toString();

                if(email.equals("")||password.equals(""))
                    Toast.makeText(LoginActivity.this, "All fields are mandatory", Toast.LENGTH_SHORT).show();
                else{
                    // Perform login operation asynchronously
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                // Check if the user exists in the database
                                User user = sdb.userDao().login(email, password);

                                if (user != null) {
                                    // Successful login, handle accordingly
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "You logged in!", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                            startActivity(intent);
                                        }
                                    });
                                } else {
// Invalid credentials, handle accordingly
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Toast.makeText(LoginActivity.this, "Invalid username or password.", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }catch (Exception e) {
                                // Handle exceptions (e.g., database errors) appropriately
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
        });
        binding.signupText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, CreateAccountActivity.class);
                startActivity(intent);
            }
        });
    }
}
