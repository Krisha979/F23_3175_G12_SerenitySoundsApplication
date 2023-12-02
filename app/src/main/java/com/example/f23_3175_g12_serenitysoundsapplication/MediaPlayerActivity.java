package com.example.f23_3175_g12_serenitysoundsapplication;


import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.f23_3175_g12_serenitysoundsapplication.Model.Song;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;

public class MediaPlayerActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private Song musicItem;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mediaplayer);

        // Retrieve the identifier from the Intent
        String musicItemId = getIntent().getStringExtra("musicItemId");

        // Fetch the full details of the selected song using the identifier
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("music").child(musicItemId);
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Song selectedMusic = dataSnapshot.getValue(Song.class);
                // Now you have the full details of the selected song
                // Use this information to set up your MediaPlayer or perform other actions
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle errors
            }
        });
    }
}
