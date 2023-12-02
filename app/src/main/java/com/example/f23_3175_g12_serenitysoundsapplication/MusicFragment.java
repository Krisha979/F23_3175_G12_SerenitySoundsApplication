package com.example.f23_3175_g12_serenitysoundsapplication;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.OpenableColumns;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.f23_3175_g12_serenitysoundsapplication.Adapter.FileAdapter;
import com.example.jean.jcplayer.model.JcAudio;
import com.example.jean.jcplayer.view.JcPlayerView;
import com.google.firebase.storage.FirebaseStorage;

import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class MusicFragment extends Fragment {

    private ListView listView;
    private FileAdapter fileAdapter;
    private List<String> fileNames;
    private MediaPlayer mediaPlayer;
    private JcPlayerView jcPlayerView;
    private List<JcAudio> jcAudios = new ArrayList<>();


    public MusicFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);
// Adjust the background image (GIF) programmatically
        ImageView imageViewBackground = rootView.findViewById(R.id.imageViewBackground);
        imageViewBackground.setImageResource(R.drawable.music_playerbg); // Replace with your actual GIF file name

        // Initialize views
        listView = rootView.findViewById(R.id.listView);
        // Initialize JcPlayerView
        jcPlayerView = rootView.findViewById(R.id.jcplayer);

        // Initialize Firebase Storage
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();

        // Load file names from Firebase Storage
        loadFileNames(storageRef);

        // Initialize file names list
        fileNames = new ArrayList<>();

        // Create custom adapter
        fileAdapter = new FileAdapter(requireContext(), fileNames);

        // Set the adapter to the ListView
        listView.setAdapter(fileAdapter);
        // Set item click listener to play music
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedMusic = fileNames.get(position);
                playMusic(storageRef.child(selectedMusic));
            }
        });


        return rootView;
    }

    private void stopMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    private void loadFileNames(StorageReference storageRef) {
        // List all items in the path
        storageRef.listAll()
                .addOnSuccessListener(listResult -> {
                    // Clear existing file names and JcAudios
                    fileNames.clear();
                    jcAudios.clear();

                    // Add new file names and JcAudios to the lists
                    for (StorageReference item : listResult.getItems()) {
                        String fileName = item.getName();
                        fileNames.add(fileName);

                        // Convert the StorageReference to a public download URL
                        item.getDownloadUrl().addOnSuccessListener(uri -> {
                            String publicUrl = uri.toString();
                            JcAudio jcAudio = JcAudio.createFromURL(item.getName(), publicUrl);
                            jcAudios.add(jcAudio);

                            // Notify the adapter that data has changed
                            fileAdapter.notifyDataSetChanged();
                        }).addOnFailureListener(exception -> {
                            // Handle any errors
                            Toast.makeText(requireContext(), "Failed to load file URL", Toast.LENGTH_SHORT).show();
                        });
                    }
                })
                .addOnFailureListener(exception -> {
                    // Handle any errors
                    Toast.makeText(requireContext(), "Failed to load file names", Toast.LENGTH_SHORT).show();
                });
    }
    private void playMusic(StorageReference musicRef) {
        // Stop any currently playing music
        stopMusic();

        try {
            // Set the data source to the music URL
            musicRef.getDownloadUrl().addOnSuccessListener(uri -> {
                String publicUrl = uri.toString();

                // Create JcAudio object
                JcAudio jcAudio = JcAudio.createFromURL(fileNames.toString(), publicUrl);

                // Clear the existing playlist and add the new audio
                jcPlayerView.initPlaylist(jcAudios, null);
                jcPlayerView.addAudio(jcAudio);

                // Highlight the currently playing song in the ListView
                fileAdapter.setSelectedPosition(fileNames.indexOf(jcAudio.getTitle()));


                // Start playing the selected audio
                jcPlayerView.playAudio(jcAudio);
            }).addOnFailureListener(exception -> {
                // Handle any errors
                Toast.makeText(requireContext(), "Failed to play music", Toast.LENGTH_SHORT).show();
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(requireContext(), "Failed to play music", Toast.LENGTH_SHORT).show();
        }
    }

    }