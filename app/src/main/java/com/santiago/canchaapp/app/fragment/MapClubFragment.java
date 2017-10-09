package com.santiago.canchaapp.app.fragment;


import android.app.Activity;
import android.content.Context;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.dominio.Horario;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static android.widget.Toast.LENGTH_SHORT;
import static com.google.android.gms.location.places.AutocompleteFilter.TYPE_FILTER_ADDRESS;
import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CLUB;
import static com.santiago.canchaapp.app.otros.TextUtils.textoOVacio;

public class MapClubFragment extends Fragment implements OnMapReadyCallback {

    private static final LatLng CAPITAL_FEDERAL = new LatLng(-34.603371, -58.451497);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int ZOOM_PUNTO_INICIAL = 11;
    private static final int ZOOM = 15;

    private Activity activity;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;
    private PlaceAutocompleteFragment autocompleteFragment;

    private LatLng ubicacion;

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_map_club, container, false);
        activity = getActivity();
        inicializarBuscador();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map_club);
        ButterKnife.bind(this, view);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { //quito las validaciones por ahora
            //if (locationLatLng != null)
                abrirFragmentSiguiente();
            //else
            //    Toast.makeText(activity.getApplicationContext(), R.string.txtSeleccionarClub, Toast.LENGTH_SHORT).show();
            }
        });

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Seleccionar ubicación");

        return view;
    }

    private void inicializarBuscador() {
        autocompleteFragment = (PlaceAutocompleteFragment)
                activity.getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter(TYPE_FILTER_ADDRESS)
                .build();
        autocompleteFragment.setFilter(typeFilter);
        autocompleteFragment.setHint(getResources().getString(R.string.txtHintAutocompleteMapa));
        autocompleteFragment.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {
                setearUbicacion(place.getLatLng(), ZOOM);
            }

            @Override
            public void onError(Status status) {
                Toast.makeText(activity.getApplicationContext(), status.toString(), LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        inicializarUbicacion();
    }

    private void inicializarUbicacion() {
        if(ubicacion == null)
            pedirUbicacionActual();
        else {
            setearUbicacion(ubicacion, ZOOM);
        }
    }

    private void setearUbicacion(LatLng ubicacion, float zoom) {
        this.ubicacion = ubicacion;
        actualizarBuscador(textoOVacio(obtenerDireccion(ubicacion.latitude, ubicacion.longitude)));
        actualizarMapa(ubicacion, zoom);
    }

    private void actualizarMapa(LatLng ubicacionLatLng, float zoom) {
        mMap.clear();
        mMap.addMarker(new MarkerOptions().position(ubicacionLatLng)
                .title(getResources().getString(R.string.txtTituloMarcadorMapaClub)));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng, zoom));
    }

    private void actualizarBuscador(String direccion) {
        if (autocompleteFragment.isVisible()) {
            autocompleteFragment.setText(direccion);
        }

    }

    private void ponerMapaEnUbicacionDefault() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAPITAL_FEDERAL, ZOOM_PUNTO_INICIAL));
    }

    private void pedirUbicacionActual() {
        if (checkSelfPermission(activity, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            generarUbicacionActual();
        } else {
            pedirPermisosParaUbicacion();
        }
    }

    private void generarUbicacionActual() {
        ponerMapaEnUbicacionDefault();
        inicializarLocationService();
    }

    private void inicializarLocationService() {
        if (checkSelfPermission(activity, ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestSingleUpdate(new Criteria(), new LocationListener() {
                @Override
                public void onLocationChanged(Location ubicacionActual) {
                    if (ubicacionActual != null) {
                        double lat = ubicacionActual.getLatitude();
                        double lon = ubicacionActual.getLongitude();
                        setearUbicacion(new LatLng(lat, lon), ZOOM);
                    }
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) { }

                @Override
                public void onProviderEnabled(String s) { }

                @Override
                public void onProviderDisabled(String s) { }
            }, null);
        }
    }

    private void pedirPermisosParaUbicacion() {
        requestPermissions(new String[]{ ACCESS_FINE_LOCATION }, PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
    }

    private String obtenerDireccion(double lat, double lon) {
        Geocoder geoCoder = new Geocoder(activity, Locale.getDefault());
        List<Address> matches = null;
        try {
            matches = geoCoder.getFromLocation(lat, lon, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return (matches != null)
                ? (matches.isEmpty() ? null : matches.get(0).getAddressLine(0))
                : null;
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

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED) {
                    generarUbicacionActual();
                }
                else {
                    ponerMapaEnUbicacionDefault();
                }
            break;
            default: ponerMapaEnUbicacionDefault(); break;
        }
    }

    private void abrirFragmentSiguiente() {
        insertClub();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, ConfirmarClub.nuevaInstancia(), REGISTRAR_CLUB.toString())
                .addToBackStack(null)
                .commit();
    }

    private void insertClub() {
        Bundle args = getArguments();
        if (args != null) {
            String nombreClub = args.getString("nombreClub");
            String telefono = args.getString("telefono");
            String email = args.getString("email");
            Horario rangoHorario = (Horario) args.getSerializable("rangoHorario");
            String direccion = obtenerDireccion(ubicacion.latitude, ubicacion.longitude);
            UUID uuid = UUID.randomUUID();
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            Club club = new Club(uuid, nombreClub, direccion, ubicacion, email, telefono, rangoHorario);
            DataBase.getInstancia().insertClub(user, club);
        }
    }

}