package com.example.f23_3175_g12_serenitysoundsapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;


public class LocationFragment extends Fragment implements OnMapReadyCallback {
    private GoogleMap mMap;


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
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        LatLng douglasNW = new LatLng(49.2036, 122.9127);
        LatLng douglasC = new LatLng(49.2881, 122.7921);
        LatLng KingGeorge = new LatLng(49.1827, 122.8446);
        LatLng Home = new LatLng(49.1126784, 122.8406784);

        // Add markers
        addMarker(googleMap, douglasNW, "Douglas NW", "Description for Douglas NW");
        addMarker(googleMap, douglasC, "Douglas Central", "Description for Douglas Central");
        addMarker(googleMap, KingGeorge, "King George", "Description for King George");
        addMarker(googleMap, Home, "Home", "Your Home Description");

        // Optionally, move the camera to a specific location (e.g., Home)
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(Home, 13.0f));
    }

    private void addMarker(GoogleMap googleMap, LatLng position, String title, String snippet) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet);

        googleMap.addMarker(markerOptions);
    }
}