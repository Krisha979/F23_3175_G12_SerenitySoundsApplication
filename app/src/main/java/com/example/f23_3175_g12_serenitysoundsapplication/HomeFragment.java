package com.example.f23_3175_g12_serenitysoundsapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.f23_3175_g12_serenitysoundsapplication.Adapter.DashboardAdapter;
import com.example.f23_3175_g12_serenitysoundsapplication.Model.MeditationTypes;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {
    List<MeditationTypes> ImageList = new ArrayList<>();
    Toast CurrToast;

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
        ImageView imgViewLarge = view.findViewById(R.id.imgViewLarge);
        DashboardAdapter myAdapter = new DashboardAdapter(ImageList);
        gridViewImages.setAdapter(myAdapter);
        gridViewImages.setNumColumns(2);
       // gridViewImages.setVerticalSpacing(8);
        // gridViewImages.setHorizontalSpacing(8);

        gridViewImages.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                imgViewLarge.setImageResource(ImageList.get(i).getImgPic());
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