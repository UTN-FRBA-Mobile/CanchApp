package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.DateUtils;

import org.w3c.dom.Text;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static com.santiago.canchaapp.app.otros.DateUtils.textoDia;

public class ListaHorariosFragment extends Fragment {

    private static String ARG_DIA = "dia";

    private static String ARG_POSICION_DIA = "pos_dia";

    @BindView(R.id.fecha_horarios)
    public TextView fecha;

    @BindView(R.id.dia_prev)
    public ImageView flechaDiaAnterior;

    @BindView(R.id.dia_next)
    public ImageView flechaDiaSiguiente;

/*
    @BindView(R.id.recycler_view_reservas)
    public RecyclerView reservasRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private ReservasAdapter adapter;*/

    public static ListaHorariosFragment nuevaInstancia(Date dia, int posicionDia) {
        ListaHorariosFragment fragment = new ListaHorariosFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_DIA, dia);
        args.putInt(ARG_POSICION_DIA, posicionDia);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_lista_horarios, container, false);
        cargarVista(rootView);
        return rootView;
    }

    private void cargarVista(View rootView) {
        ButterKnife.bind(this, rootView);

        if (primerDia()) {
            fecha.setText(getResources().getString(R.string.txtHorariosFechaHoy));
            flechaDiaAnterior.setVisibility(INVISIBLE);
        } else {
            fecha.setText(getTextoFecha());
            if (ultimoDia()) {
                flechaDiaSiguiente.setVisibility(INVISIBLE);
            }
        }

    }

    private String getTextoFecha() {
        Date dia = (Date) getArguments().getSerializable(ARG_DIA);
        return textoDia(dia);
    }

    private boolean primerDia() {
        return getArguments().getInt(ARG_POSICION_DIA) == 0;
    }

    private boolean ultimoDia() {
        return getArguments().getInt(ARG_POSICION_DIA) == 6;
    }

}