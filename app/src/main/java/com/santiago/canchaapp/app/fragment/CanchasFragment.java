package com.santiago.canchaapp.app.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Slide;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.CanchasAdapter;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.DataBase;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.google.firebase.database.DatabaseError.PERMISSION_DENIED;
import static com.santiago.canchaapp.app.otros.FragmentTags.REGISTRAR_CANCHA;

public class CanchasFragment extends Fragment {

    private static String ARG_ID_CLUB = "idClub";
    private static String ARG_MI_CLUB = "esMiClub";
    private RecyclerView.LayoutManager layoutManager;
    private CanchasAdapter adapter;
    private Context context;
    @BindView(R.id.recycler_view_canchas)
    public RecyclerView canchasRecyclerView;
    @BindView(R.id.progressBar)
    public ProgressBar progressBar;
    @BindView(R.id.fab)
    public FloatingActionButton agregarCancha;


    public static CanchasFragment nuevaInstancia(String idClub, Boolean esMiClub) {
        CanchasFragment fragment = new CanchasFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_MI_CLUB, esMiClub);
        args.putString(ARG_ID_CLUB, idClub);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_canchas, container, false);
        ButterKnife.bind(this, rootView);
        context = getActivity().getApplicationContext();

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        canchasRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new CanchasAdapter(getContext(), esMiClub());
        canchasRecyclerView.setAdapter(adapter);

        getCanchasClub();
        setAgregarCancha();
        return rootView;
    }

    private void getCanchasClub() {
        progressBar.setVisibility(VISIBLE);
        DatabaseReference refCanchasClub = DataBase.getInstancia().getReferenceCanchasClub(idClub());
        ChildEventListener childEventListener = new ChildEventListener() {
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
                progressBar.setVisibility(GONE);
                if (databaseError.getCode() != PERMISSION_DENIED) {
                    Toast.makeText(getContext(), R.string.txtErrorDescargandoInfo, Toast.LENGTH_LONG).show();
                }
            }

            private void actualizarLista(DataSnapshot snapshotCancha) {
                progressBar.setVisibility(GONE);
                if(snapshotCancha != null && snapshotCancha.getValue() != null) {
                    Cancha cancha = snapshotCancha.getValue(Cancha.class);
                    adapter.actualizarLista(cancha);
                }
            }

        };
        refCanchasClub.addChildEventListener(childEventListener);
    }

    private void setAgregarCancha() {
        agregarCancha.setVisibility(esMiClub() ? VISIBLE : GONE);
        agregarCancha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(DataBase.getInstancia().isOnline(context)) {
                    abrirFragmentSiguiente();
                }
                else {
                    showToast(R.string.txtSinConexion);
                }
            }
        });
    }

    private void showToast(int idTxt) {
        Toast.makeText(context, idTxt, Toast.LENGTH_SHORT).show();
    }

    private void abrirFragmentSiguiente() {
        Fragment agregarCanchaFragment = AgregarCanchaFragment.nuevaInstancia();
        agregarCanchaFragment.setExitTransition(new Slide(Gravity.LEFT));
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.club_layout, agregarCanchaFragment, REGISTRAR_CANCHA.toString())
                .addToBackStack(null)
                .commit();
    }

    private boolean esMiClub() {
        return getArguments().getBoolean(ARG_MI_CLUB);
    }

    private String idClub() {
        return getArguments().getString(ARG_ID_CLUB);
    }

}
