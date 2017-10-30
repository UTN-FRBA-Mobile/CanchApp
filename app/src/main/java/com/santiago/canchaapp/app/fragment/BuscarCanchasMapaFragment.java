package com.santiago.canchaapp.app.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.DataBase;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class BuscarCanchasMapaFragment extends Fragment implements OnMapReadyCallback {


    private static final LatLng CAPITAL_FEDERAL = new LatLng(-34.603371, -58.451497);
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int ZOOM_PUNTO_INICIAL = 11;
    private static final int ZOOM = 15;

    //private Activity activity;
    private GoogleMap mMap;
    private SupportMapFragment mapFragment;

    private LatLng ubicacion;

    public static BuscarCanchasMapaFragment nuevaInstancia() {
        return new BuscarCanchasMapaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_mapa_clubes, container, false);
        //activity = getActivity();
        mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapa_clubes);
        ButterKnife.bind(this, view);

        ubicacion = CAPITAL_FEDERAL;

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
        inicializarUbicacion();
    }

    private void inicializarUbicacion() {
        setearUbicacion(ubicacion, ZOOM);
    }

    private void setearUbicacion(LatLng ubicacion, float zoom) {
        this.ubicacion = ubicacion;
        actualizarMapa(ubicacion, zoom);
    }

    private void actualizarMapa(LatLng ubicacionLatLng, float zoom) {
        mMap.clear();

        //obtiene clubes
        DatabaseReference refDatosClubes = DataBase.getInstancia().getReferenceClubes();

        refDatosClubes.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        cargarUbicacionDeClubes((Map<String,Object>) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //tengo que overridearlo
                    }
                });

        Bitmap miUbicacion = BitmapFactory.decodeResource(getResources(), R.drawable.mi_ubicacion);

        mMap.addMarker(
                new MarkerOptions()
                        .position(ubicacionLatLng)
                        .title("Mi ubicación")
                        .icon(BitmapDescriptorFactory.fromBitmap(resizearBitmap(miUbicacion, 0.05f))));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionLatLng, zoom));
    }

    private void cargarUbicacionDeClubes(Map<String,Object> users) {
        for (Map.Entry<String, Object> entry : users.entrySet()){

            Map singleUser = (Map) entry.getValue();

            LatLng punto = getLocationFromAddress(getContext(), (String) singleUser.get("direccion"));
            String nombre = (String) singleUser.get("nombre");

            Bitmap ubicacion_club = BitmapFactory.decodeResource(getResources(), R.drawable.ubicacion_club);

            mMap.addMarker(
                    new MarkerOptions()
                            .position(punto)
                            .title(nombre)
                            .icon(BitmapDescriptorFactory.fromBitmap(resizearBitmap(ubicacion_club, 0.053f))));
        }
    }

    public Bitmap resizearBitmap(Bitmap bitmapIn, float porcentaje) {
        Bitmap bitmapOut = Bitmap.createScaledBitmap(bitmapIn,
                Math.round(bitmapIn.getWidth() * porcentaje),
                Math.round(bitmapIn.getHeight() * porcentaje), false);
        return bitmapOut;
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
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

    private void generarUbicacionActual() {
        ponerMapaEnUbicacionDefault();
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


