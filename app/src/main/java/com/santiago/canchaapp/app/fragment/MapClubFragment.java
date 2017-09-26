package com.santiago.canchaapp.app.fragment;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import android.widget.Button;

import com.santiago.canchaapp.R;
import android.Manifest;


import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_CANCHAS;

public class MapClubFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private Location location;
    private Boolean mLocationPermissionGranted;

    @BindView(R.id.btnContinuar)
    public Button continuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map_club, container, false);
        activity = getActivity();
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_club);
        mapFragment.getMapAsync(this);

        ButterKnife.bind(this, view);

        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragment();
            }
        });
        return view;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        enableMyLocation();
    }
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else
            mLocationPermissionGranted = true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        }
        updateLocationUI();
    }
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
    }
    private void abrirFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, CanchasFragment.nuevaInstancia(), MIS_CANCHAS.toString())
                .addToBackStack(null)
                .commit();
    }


}