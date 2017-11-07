package com.santiago.canchaapp.app.fragment;

import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.ClubesAdapter;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.servicios.Sesion;


import butterknife.BindView;
import butterknife.ButterKnife;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.content.pm.PackageManager.PERMISSION_GRANTED;
import static android.support.v4.content.ContextCompat.checkSelfPermission;
import static com.google.firebase.database.DatabaseError.PERMISSION_DENIED;

public class BuscarCanchasListaFragment extends Fragment {

    private static String ARG_CANCHAS = "canchas";

    @BindView(R.id.recycler_view_clubes)
    public RecyclerView clubesRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ClubesAdapter adapter;

    private LatLng locacion;

    public static BuscarCanchasListaFragment nuevaInstancia() {
        return new BuscarCanchasListaFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_clubes, container, false);

        cargarVista(rootView, locacion);

        locacion = new LatLng(0, 0);
        inicializarLocationService();

        return rootView;
    }

    private void inicializarLocationService() {
        if ((checkSelfPermission(getActivity(), ACCESS_FINE_LOCATION) == PERMISSION_GRANTED) && laLocacionEstaActivada(getActivity())) {
            LocationManager locationManager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestSingleUpdate(new Criteria(), new LocationListener() {
                @Override
                public void onLocationChanged(Location ubicacionActual) {
                    setearUbicacion(ubicacionActual);
                }

                @Override
                public void onStatusChanged(String s, int i, Bundle bundle) {
                }

                @Override
                public void onProviderEnabled(String s) {
                }

                @Override
                public void onProviderDisabled(String s) {
                }
            }, null);
        }
        else
            crearAdapterYReciclerView();
    }

    public static boolean laLocacionEstaActivada(Context context) {
        int locationMode = 0;
        try {
            locationMode = Settings.Secure.getInt(context.getContentResolver(), Settings.Secure.LOCATION_MODE);
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        return locationMode != Settings.Secure.LOCATION_MODE_OFF;
    }

    public void setearUbicacion(Location ubicacionActual)
    {
        if (ubicacionActual != null) {
            double lat = ubicacionActual.getLatitude();
            double lon = ubicacionActual.getLongitude();
            locacion = new LatLng(lat, lon);
        }
        else
            locacion = new LatLng(0, 0);

        crearAdapterYReciclerView();
    }

    private void crearAdapterYReciclerView(){
        adapter = new ClubesAdapter(getContext(), locacion);
        clubesRecyclerView.setAdapter(adapter);

        DatabaseReference refDatosClubes = DataBase.getInstancia().getReferenceClubes();
        refDatosClubes.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                actualizarLista(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                actualizarLista(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                if (databaseError.getCode() != PERMISSION_DENIED)
                    Toast.makeText(getContext(), R.string.txtErrorDescargandoInfo, Toast.LENGTH_LONG).show();
            }
        });
    }
    private void cargarVista(View rootView, final LatLng locacion) {
        ButterKnife.bind(this, rootView);

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        clubesRecyclerView.setLayoutManager(layoutManager);

    }

    private void actualizarLista(DataSnapshot snapshotClub) {
        Club club = snapshotClub.getValue(Club.class);
        //if(club.tieneCanchas())
        if (!Sesion.getInstancia().getUsuario().esMiClub(club.getUuid()))
            adapter.actualizarLista(club);
    }
}
