package com.santiago.canchaapp.app.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.support.annotation.NonNull;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;

import android.widget.Button;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santiago.canchaapp.R;

import android.Manifest;
import android.widget.Toast;


import butterknife.BindView;
import butterknife.ButterKnife;


import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_CANCHAS;

public class MapClubFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private LatLng locationLatLng;
    private Location location;
    private static final LatLng CAPITAL_FEDERAL = new LatLng(-34.609404, -58.498656);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int ZOOM_PUNTO_INICIAL = 5;
    private static final int ZOOM = 15;
    private SupportMapFragment mapFragment;

    @BindView(R.id.btnContinuar)
    public Button continuar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map_club, container, false);
        activity = getActivity();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_club);
        PlaceAutocompleteFragment autocompleteFragment = (PlaceAutocompleteFragment)
                activity.getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                LatLng myLocation = place.getLatLng();
                mMap.addMarker(new MarkerOptions().position(myLocation).title("Mi club"));
                setLocation(myLocation, ZOOM);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(activity.getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
        ButterKnife.bind(this, view);
        continuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragment();
            }
        });
        return view;
    }

    public void setLocation(LatLng location, float zoom){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
    }

    @Override
    public void onStart(){
        super.onStart();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setCapitalFederal();
        getMyLocation();
    }

    private void setCapitalFederal(){
        Projection projection = mMap.getProjection();
        Point capitalFederalPoint = projection.toScreenLocation(CAPITAL_FEDERAL);
        mMap.moveCamera(CameraUpdateFactory.zoomBy(ZOOM_PUNTO_INICIAL, capitalFederalPoint));
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            setMyLocation();
        } else {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void setMyLocation() {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                locationLatLng = new LatLng(latitude, longitude);
                setLocation(locationLatLng, ZOOM);
            }
            else
                setCapitalFederal();
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                   setMyLocation();
                }
                else
                    setCapitalFederal();

            } break;
            default: setCapitalFederal(); break;
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