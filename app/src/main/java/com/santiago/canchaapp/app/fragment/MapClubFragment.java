package com.santiago.canchaapp.app.fragment;


import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Point;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.os.Bundle;
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

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.santiago.canchaapp.R;

import android.widget.Toast;


import java.io.IOException;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;


import static com.santiago.canchaapp.app.otros.FragmentTags.MIS_CANCHAS;

public class MapClubFragment extends Fragment implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Activity activity;
    private LatLng locationLatLng;
    private Location location;
    private static final LatLng CAPITAL_FEDERAL = new LatLng(-34.603371, -58.451497);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int ZOOM_PUNTO_INICIAL = 11;
    private static final int ZOOM = 15;
    private SupportMapFragment mapFragment;
    private PlaceAutocompleteFragment autocompleteFragment;
    private String street;
    @BindView(R.id.fab)
    public FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map_club, container, false);
        activity = getActivity();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_club);
        setAutocomplete();
        ButterKnife.bind(this, view);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            if (locationLatLng != null)
                abrirFragmentSiguiente();
            else
                Toast.makeText(activity.getApplicationContext(), R.string.txtSeleccionarClub, Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void setAutocomplete() {
        autocompleteFragment = (PlaceAutocompleteFragment)
                activity.getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(AutocompleteFilter.TYPE_FILTER_ADDRESS)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint("Ubicación club");
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                street = place.getAddress().toString();
                locationLatLng = place.getLatLng();
                setLocation(locationLatLng, ZOOM);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(activity.getApplicationContext(), status.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void setLocation(LatLng location, float zoom) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(location).title("Mi club"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if(locationLatLng == null)
            getMyLocation();
        else {
            autocompleteFragment.setText(street);
            setLocation(locationLatLng, ZOOM);
        }
    }

    private void setCapitalFederal(){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAPITAL_FEDERAL, ZOOM_PUNTO_INICIAL));
    }

    private void getMyLocation() {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            generateMyLocation();
        } else {
            requestPermissions(
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

    private void generateMyLocation() {
        if (ActivityCompat.checkSelfPermission(activity, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            Criteria criteria = new Criteria();
            String provider = locationManager.getBestProvider(criteria, true);
            location = locationManager.getLastKnownLocation(provider);
            if (location != null) {
                double lat = location.getLatitude();
                double lon = location.getLongitude();
                Address address = getStreet(lat, lon);
                street = address.getAddressLine(0).toString();
                autocompleteFragment.setText(street);
                locationLatLng = new LatLng(lat, lon);
                setLocation(locationLatLng, ZOOM);
            }
            else
                setCapitalFederal();
        }

    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapFragment.isAdded()) {
            getChildFragmentManager().beginTransaction().remove(mapFragment).commitAllowingStateLoss();
        }
        if (autocompleteFragment.isAdded()) {
            getActivity().getFragmentManager().beginTransaction().remove(autocompleteFragment).commitAllowingStateLoss();
        }
    }

    private Address getStreet(double lat, double lon) {
        Geocoder geoCoder = new Geocoder(activity, Locale.getDefault());
        List<Address> matches = null;
        try {
            matches = geoCoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (matches.isEmpty() ? null : matches.get(0));
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
                    generateMyLocation();
                }
                else
                    setCapitalFederal();

            } break;
            default: setCapitalFederal(); break;
        }
    }

    private void abrirFragmentSiguiente() {
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, CanchasFragment.nuevaInstancia(), MIS_CANCHAS.toString())
                .addToBackStack(null)
                .commit();
    }


}