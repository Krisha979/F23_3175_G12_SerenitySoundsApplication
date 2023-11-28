package com.example.f23_3175_g12_serenitysoundsapplication;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.f23_3175_g12_serenitysoundsapplication.Dao.UserDao;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.User;
import com.example.f23_3175_g12_serenitysoundsapplication.SerenityDatabase.SerenityDatabase;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CreateAccountActivity extends AppCompatActivity {
    private EditText nameEditText, emailEditText, passwordEditText, dobEditText;
SerenityDatabase serenityDatabase;
    public User newuser = new User();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createacccount);

        nameEditText = findViewById(R.id.editTextName);
        emailEditText = findViewById(R.id.editTextEmail);
        passwordEditText = findViewById(R.id.editTextPassword);
        dobEditText = findViewById(R.id.editTextDob);

        serenityDatabase = Room.databaseBuilder(
                        getApplicationContext(), SerenityDatabase.class, "serenity.db")
                .build();

        Button createAccountButton = findViewById(R.id.btnCreateAccount);
        createAccountButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccountInBackground();
            }
        });
    }
    // Method to show DatePickerDialog
    public void showDatePickerDialog(View view) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        // Update the EditText with the selected date
                        String dob = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        dobEditText.setText(dob);
                    }
                },
                year,
                month,
                dayOfMonth);

        // Show the DatePickerDialog
        datePickerDialog.show();
    }
    public void createAccountInBackground() {
        // Handle create account button click
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String dob = dobEditText.getText().toString();

        // Check if the email is unique (optional)
        if (serenityDatabase.userDao().getUserByEmail(email) != null) {
            Toast.makeText(this, "Email already exists", Toast.LENGTH_SHORT).show();
            return;
        }

        User newUser = new User();
        newUser.setName(name);
        newUser.setEmail(email);
        newUser.setPassword(password);
        // Parse the dob string to Date (you may want to add error handling here)
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        try {
            String dobDate = sdf.parse(dob).toString();
            newUser.setDob(dobDate);
        } catch (ParseException e) {
            e.printStackTrace();
            // Handle the parsing error
            return;
        }
        // Perform account creation and insertion using Room Database
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                serenityDatabase.userDao().insert(newUser);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CreateAccountActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();

                        // Optionally, navigate to another activity after account creation
                        Intent intent = new Intent(CreateAccountActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }
}



