package com.santiago.canchaapp.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback;
import com.google.android.gms.maps.StreetViewPanorama;
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.santiago.canchaapp.LoginActivity;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DatosClubFragment extends Fragment {

    private static String ARG_ID_CLUB = "idClub";
    private SupportStreetViewPanoramaFragment streetView;
    private View rootView;
    private final boolean[] gotResult = new boolean[1];
    private Context context;
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
        context = getActivity().getApplicationContext();
        ButterKnife.bind(this, rootView);
        Bundle arguments = getArguments();
        getClub(arguments.getString(ARG_ID_CLUB));
        return rootView;
    }

    private void getClub(String idClub){
        //TODO mostrarSpinner
        gotResult[0] = false;
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //TODO sacarSpinner
                gotResult[0] = true;
                if(dataSnapshot.getValue() != null){
                    Club club = dataSnapshot.getValue(Club.class);
                    cargarVista(club);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                gotResult[0] = true;
                //TODO sacarSpinner
                showToast(R.string.txtMalaConexion);
            }
        };
        DatabaseReference referenceClub = DataBase.getInstancia().getReferenceClub(idClub);
        if(DataBase.getInstancia().isOnline(context)) {
            DataBase.getInstancia().setTimeoutFirebase(referenceClub, valueEventListener, getActivity(), new Runnable() {
                @Override
                public void run() {
                    if(!gotResult[0]) {
                        //TODO sacarSpinner
                        showToast(R.string.txtMalaConexion);

                    }
                }
            });
            referenceClub.addListenerForSingleValueEvent(valueEventListener);
        }
        else{
            //TODO sacarSpinner
            showToast(R.string.txtSinConexion);
        }
    }

    private void showToast(int idTxt) {
        Toast.makeText(context, idTxt, Toast.LENGTH_SHORT).show();
    }

    private void cargarVista(final Club club) {
        textoNombre.setText(club.getNombre());
        textoDireccion.setText(club.getDireccion());
        textoTelefono.setText("Teléfono: " + club.getTelefono());
        textoEmail.setText("Email: " + club.getEmail());
        textoHorario.setText(
                "Abierto de " + club.getRangoHorario().getDesde() +
                        " a " + club.getRangoHorario().getHasta() + "hs.");
        setUbication(club);
    }

    private void setUbication(final Club club) {
        streetView.getStreetViewPanoramaAsync(
                new OnStreetViewPanoramaReadyCallback() {
                    @Override
                    public void onStreetViewPanoramaReady(StreetViewPanorama panorama) {
                        panorama.setPosition(club.getCoordenadas().toLatLng());
                    }
                });
    }
}
