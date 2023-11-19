package com.example.f23_3175_g12_serenitysoundsapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;


public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;
    LatLng douglasNW  = new LatLng(49.2036, 122.9127);
    LatLng douglasC = new LatLng(49.2881, 122.7921);
    LatLng KingGeorge = new LatLng(49.1827, 122.8446);
    LatLng Brisbane = new LatLng(49.1126784, 122.8406784);

    public LocationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_location, container, false);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {

    }
}