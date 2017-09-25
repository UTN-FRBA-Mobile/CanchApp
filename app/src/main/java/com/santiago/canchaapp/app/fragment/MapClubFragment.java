package com.santiago.canchaapp.app.fragment;

import android.content.pm.PackageManager;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santiago.canchaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_CANCHAS;

public class MapClubFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;

    @BindView(R.id.btnContinuar)
    public Button continuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map_club, container, false);
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

    private void abrirFragment() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, CanchasFragment.nuevaInstancia(), MIS_CANCHAS.toString())
                .addToBackStack(null)
                .commit();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        MapStyleOptions style = MapStyleOptions.loadRawResourceStyle(getContext(), R.raw.style_json);
        mMap.setMapStyle(style);
        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
}