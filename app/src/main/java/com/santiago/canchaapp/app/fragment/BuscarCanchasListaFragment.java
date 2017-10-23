package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.ClubesAdapter;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.DataBase;
import com.santiago.canchaapp.servicios.Servidor;
import com.santiago.canchaapp.servicios.Sesion;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuscarCanchasListaFragment extends Fragment {

    private static String ARG_CANCHAS = "canchas";

    @BindView(R.id.recycler_view_clubes)
    public RecyclerView clubesRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ClubesAdapter adapter;

    public static BuscarCanchasListaFragment nuevaInstancia() {
        return new BuscarCanchasListaFragment();
        //Bundle args = new Bundle();
        //args.putSerializable(ARG_CANCHAS, (Serializable) datosDeClubes());
        //fragment.setArguments(args);
        //return fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_clubes, container, false);
        cargarVista(rootView);
        return rootView;
    }

    private void cargarVista(View rootView) {
        ButterKnife.bind(this, rootView);

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        clubesRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new ClubesAdapter(getContext());
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
            public void onChildRemoved(DataSnapshot dataSnapshot) {}
            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {}
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), R.string.txtErrorDescargandoInfo, Toast.LENGTH_LONG).show();
            }

            private void actualizarLista(DataSnapshot snapshotAlquiler) {
                Club club = snapshotAlquiler.getValue(Club.class);
                //if(club.tieneCanchas())
                if(!Sesion.getInstancia().getUsuario().esMiClub(club.getUuid()))
                    adapter.actualizarLista(club);
            }

        });
    }

}
