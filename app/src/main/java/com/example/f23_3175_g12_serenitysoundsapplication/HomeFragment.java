package com.example.f23_3175_g12_serenitysoundsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.f23_3175_g12_serenitysoundsapplication.Adapter.DashboardAdapter;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.MeditationTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    List<MeditationTypes> ImageList = new ArrayList<>();
    Toast CurrToast;
    MediaPlayer mediaPlayer = null;
    public HomeFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        AddData();
        GridView gridViewImages = view.findViewById(R.id.gridViewMeditationTypes);
        TextView txtViewTypeTitle = view.findViewById(R.id.meditationTypeName);
        ImageView playbtn = view.findViewById(R.id.imageViewPlayPause);
        DashboardAdapter myAdapter = new DashboardAdapter(ImageList);
        gridViewImages.setAdapter(myAdapter);
        gridViewImages.setNumColumns(2);
       // gridViewImages.setVerticalSpacing(8);
        // gridViewImages.setHorizontalSpacing(8);
      playbtn.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View v) {
              if (mediaPlayer == null) {
                  // If MediaPlayer is null, create and start
                  mediaPlayer = MediaPlayer.create(requireContext(), R.raw.forestwalk);
                  mediaPlayer.start();
                  playbtn.setImageResource(R.drawable.pause);
              } else {
                  // If MediaPlayer is playing, pause; otherwise, resume
                  if (mediaPlayer.isPlaying()) {
                      mediaPlayer.pause();
                      playbtn.setImageResource(R.drawable.play);
                  } else {
                      mediaPlayer.start();
                      playbtn.setImageResource(R.drawable.pause);
                  }
              }
          }
      });
        gridViewImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                txtViewTypeTitle.setText(ImageList.get(i).getImgName()+ " Meditation Music");
                if (CurrToast != null) {
                    CurrToast.cancel();
                }
                CurrToast = Toast.makeText(requireContext(),
                        "click on " + ImageList.get(i).getImgName(), Toast.LENGTH_LONG);
                CurrToast.show();
            }
        });
        return view;
    }

    private void AddData(){
        ImageList.add(
                new MeditationTypes(1,"Calm",R.drawable.calm));
        ImageList.add(
                new MeditationTypes(2,"Sleep",R.drawable.sleep));
        ImageList.add(
                new MeditationTypes(3,"Relax",R.drawable.relax));
        ImageList.add(
                new MeditationTypes(4,"Focus",R.drawable.focus));

    }
}