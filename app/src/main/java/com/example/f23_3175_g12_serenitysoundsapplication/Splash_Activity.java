package com.example.f23_3175_g12_serenitysoundsapplication;

import static com.spotify.sdk.android.auth.AccountsQueryParameters.CLIENT_ID;
import static com.spotify.sdk.android.auth.AccountsQueryParameters.REDIRECT_URI;
import static com.spotify.sdk.android.auth.LoginActivity.REQUEST_CODE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Window;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.User;
import com.example.f23_3175_g12_serenitysoundsapplication.SerenityDatabase.SerenityDatabase;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class Splash_Activity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 5000; // 2 seconds
    List<User> UsersList = new ArrayList<>();
    SerenityDatabase sdb;
//    private static final String CLIENT_ID = "2c3611d903dd424ead746742f6475c9e";
//    private static final String REDIRECT_URI = "com.serenitysoundsapplication://callback";
//    private static final int REQUEST_CODE = 1337;
//    private static final String SCOPES = "user-read-recently-played,user-library-modify,user-read-email,user-read-private";
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        UsersList = ReadStudentCSV();
        Log.d("DBDEMO",UsersList.size() + " Students in the file");

//        binding.listViewStudents.setAdapter(new StudentAdapter(StudentList));

        sdb = Room.databaseBuilder(
                        getApplicationContext(), SerenityDatabase.class,
                        "serenityDatabase.db")
                .build();
        ExecutorService executorService
                = Executors.newSingleThreadExecutor();
        executorService.execute(new Runnable() {
            @Override
            public void run() {
                sdb.userDao().insertUserFromList(UsersList);
                List<User> StudentsFromDB
                        = sdb.userDao().GetAllUsers();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
//                        binding.listViewStudents
//                                .setAdapter(new StudentAdapter(StudentsFromDB));
                    }
                });
            }
        });
        // Delay and navigate to the main activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash_Activity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, SPLASH_DELAY);
    }

    private List<User> ReadStudentCSV(){
        List<User> Users = new ArrayList<>();
        InputStream inputStream = getResources()
                .openRawResource(R.raw.user);
        BufferedReader reader
                = new BufferedReader(new InputStreamReader(inputStream));
        String userLine;

        try {
            if ((userLine = reader.readLine()) != null){
                //process header
            }
            while((userLine = reader.readLine()) != null){
                String[] eachUserFields = userLine.split(",");
                User eachUser =
                        new User(eachUserFields[0],
                                eachUserFields[1], eachUserFields[2],eachUserFields[3]);
                Users.add(eachUser); //cannot add to a null list
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally{
            try {
                inputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        return Users;
    }
}