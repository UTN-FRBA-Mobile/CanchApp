package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DatosClubFragment extends Fragment {

    @BindView(R.id.nombre)
    public TextView textoNombre;

    @BindView(R.id.direccion)
    public TextView textoDireccion;

    @BindView(R.id.telefono)
    public TextView textoTelefono;

    @BindView(R.id.email)
    public TextView textoEmail;

    @BindView(R.id.horario)
    public TextView textoHorario;

    public SupportStreetViewPanoramaFragment streetView;

    private static String ARG_ID_CLUB = "idClub";

    private static final LatLng SYDNEY = new LatLng(-33.87365, 151.20689);

    private View rootView;

    public static DatosClubFragment nuevaInstancia(String idClub) {
        DatosClubFragment fragment = new DatosClubFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ID_CLUB, idClub);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootView = inflater.inflate(R.layout.fragment_datos_club, container, false);
        streetView = (SupportStreetViewPanoramaFragment) getChildFragmentManager().findFragmentById(R.id.streetviewfragment);
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        getClub(arguments.getString(ARG_ID_CLUB), savedInstanceState);

        return rootView;
    }

    private void getClub(String idClub, final Bundle savedInstanceState){
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Club club = dataSnapshot.getValue(Club.class);
                    cargarVista(club, savedInstanceState);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        DatabaseReference referenceClub = DataBase.getInstancia().getReferenceClub(idClub);
        referenceClub.addListenerForSingleValueEvent(valueEventListener);
    }

    private void cargarVista(final Club club, Bundle savedInstanceState) {
        textoNombre.setText(club.getNombre());
        textoDireccion.setText(club.getDireccion());
        textoTelefono.setText("Teléfono: " + club.getTelefono());
        textoEmail.setText("Email: " + club.getEmail());
        textoHorario.setText(
                "Abierto de " + club.getRangoHorario().getDesde() +
                        " a " + club.getRangoHorario().getHasta() + "hs.");
        setUbication(club, savedInstanceState);
    }

    private void setUbication(final Club club, final Bundle savedInstanceState) {
        streetView.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        if (savedInstanceState == null) {
                            panorama.setPosition(SYDNEY);
                        }
                    }
                });
    }
}
