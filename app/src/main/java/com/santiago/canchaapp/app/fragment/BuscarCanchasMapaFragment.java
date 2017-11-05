package com.santiago.canchaapp.app.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.FragmentTags;
import com.santiago.canchaapp.dominio.DataBase;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static com.santiago.canchaapp.app.otros.FragmentTags.CLUB;

public class BuscarCanchasMapaFragment extends Fragment implements OnMapReadyCallback, OnMarkerClickListener {

    private static final LatLng CAPITAL_FEDERAL = new LatLng(-34.603371, -58.451497);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int ZOOM_PUNTO_INICIAL = 11;
    private static final int ZOOM = 15;

    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private Map<LatLng, String> ubicaciones = new HashMap<>();

    private LatLng ubicacion;
    private String clubSeleccionado;

    @BindView(R.id.verDetalle)
    public Button btnVerDetalle;

    public static BuscarCanchasMapaFragment nuevaInstancia() {
        return new BuscarCanchasMapaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mapa_clubes, container, false);

        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa_clubes);
        ButterKnife.bind(this, view);

        btnVerDetalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragment(ClubFragment.nuevaInstancia(clubSeleccionado, true), CLUB);;
                }
            });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        googleMap.setOnMarkerClickListener(this);
        inicializarUbicacion();
        cargarClubes();
    }

    @Override
    public boolean onMarkerClick(final Marker marker) {

        LatLng ubicacionMarker = marker.getPosition();

        boolean coincideLatitud = ubicacionMarker.latitude == ubicacion.latitude;
        boolean coincideLongitud = ubicacionMarker.longitude == ubicacion.longitude;

        if(coincideLatitud && coincideLongitud) {
            showToast("Tu ubicación");
        } else {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(ubicacionMarker, ZOOM));
            marker.showInfoWindow();

            final Button verDetalle = getActivity().findViewById(R.id.verDetalle);
            verDetalle.setText("   Ver más de " + marker.getTitle() + "   ");
            verDetalle.setVisibility(View.VISIBLE);

            mMap.setOnInfoWindowCloseListener(
                    new GoogleMap.OnInfoWindowCloseListener() {
                          @Override
                          public void onInfoWindowClose(Marker marker) {
                              verDetalle.setVisibility(View.GONE);
                          }
                    }
            );

            String uuid = ubicaciones.get(ubicacionMarker);
            clubSeleccionado = uuid;
        }
        return true;
    }

    private void abrirFragment(Fragment fragment, FragmentTags tag) {
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.content_frame, fragment, tag.toString())
                .addToBackStack(null)
                .commit();
    }

    private void inicializarUbicacion() {
        if(ubicacion == null)
            pedirUbicacionActual();
        else {
            setearUbicacion(ubicacion, ZOOM);
        }
    }

    private void pedirUbicacionActual() {
        if (checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
            if (laLocacionEstaActivada(getContext())){
                generarUbicacionActual();
            } else {
                actualizarMapa(CAPITAL_FEDERAL, ZOOM_PUNTO_INICIAL, false);
            }
        } else {
            pedirPermisosParaUbicacion();
        }
    }

    public static boolean laLocacionEstaActivada(Context context) {
        int locationMode = 0;
        String locationProviders;

        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    private void generarUbicacionActual() {
        ponerMapaEnUbicacionDefault();
        inicializarLocationService();
    }

    private void inicializarLocationService() {
        if (checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) {
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

    private void setearUbicacion(LatLng ubicacion, float zoom) {
        this.ubicacion = ubicacion;
        actualizarMapa(ubicacion, zoom, true);
    }

    private void actualizarMapa(LatLng ubicacionLatLng, float zoom, Boolean ubicacionActivada) {
        if (ubicacionActivada) {
            Bitmap miUbicacion = BitmapFactory.decodeResource(getResources(), R.drawable.mi_ubicacion);

            mMap.addMarker(
                    new MarkerOptions()
                            .position(ubicacionLatLng)
                            .title("Mi ubicación")
                            .icon(BitmapDescriptorFactory.fromBitmap(resizearBitmap(miUbicacion, 0.05f))));
        }

        ubicacion = CAPITAL_FEDERAL;
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng, zoom));
    }

    private void cargarClubes(){
        DatabaseReference refDatosClubes = DataBase.getInstancia().getReferenceClubes();

        refDatosClubes.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        cargarUbicacionDeClubes((Map<String, Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //tengo que overridearlo
                    }
                });
    }

    private void cargarUbicacionDeClubes(Map<String,Object> clubes) {
        for (Map.Entry<String, Object> entry : clubes.entrySet()){
            try {
                Map club = (Map) entry.getValue();

                LatLng punto = obtenerLocacionDeUnaDireccion(getContext(), (String) club.get("direccion"));
                String nombre = (String) club.get("nombre");

                Bitmap ubicacion_club = BitmapFactory.decodeResource(getResources(), R.drawable.ubicacion_club);

                mMap.addMarker(
                        new MarkerOptions()
                                .position(punto)
                                .title(nombre)
                                .icon(BitmapDescriptorFactory.fromBitmap(resizearBitmap(ubicacion_club, 0.053f))));

                ubicaciones.put(punto, entry.getKey());
            }
            catch (Exception e) {
                System.out.println(e.toString());
            }
        }
    }

    public Bitmap resizearBitmap(Bitmap bitmapIn, float porcentaje) {
        Bitmap bitmapOut = Bitmap.createScaledBitmap(bitmapIn,
                Math.round(bitmapIn.getWidth() * porcentaje),
                Math.round(bitmapIn.getHeight() * porcentaje), false);
        return bitmapOut;
    }

    public LatLng obtenerLocacionDeUnaDireccion(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;

        try {
            // May throw an IOException
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();

            p1 = new LatLng(location.getLatitude(), location.getLongitude() );

        } catch (IOException ex) {

            ex.printStackTrace();
        }

        return p1;
    }

    private void ponerMapaEnUbicacionDefault() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(CAPITAL_FEDERAL, ZOOM_PUNTO_INICIAL));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mapFragment.isAdded()) {
            getChildFragmentManager().beginTransaction().remove(mapFragment).commitAllowingStateLoss();
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

    private void showToast(String mensaje){
        Toast.makeText(getActivity().getApplicationContext(),mensaje, Toast.LENGTH_LONG).show();

    }
}


