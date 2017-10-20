package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.HorariosAdapter;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.SlotReserva;
import com.santiago.canchaapp.servicios.Servidor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.INVISIBLE;
import static com.santiago.canchaapp.app.otros.DateUtils.textoDia;

public class ListaHorariosFragment extends Fragment {

    private static String ARG_CANCHA = "cancha";

    private static String ARG_DIA = "dia";

    private static String ARG_POSICION_DIA = "pos_dia";

    private static String ARG_HORARIOS = "horarios";

    @BindView(R.id.fecha_horarios)
    public TextView fecha;

    @BindView(R.id.dia_prev)
    public ImageView flechaDiaAnterior;

    @BindView(R.id.dia_next)
    public ImageView flechaDiaSiguiente;

    @BindView(R.id.recycler_view_horarios)
    public RecyclerView horariosRecyclerView;

    private RecyclerView.LayoutManager layoutManager;

    private HorariosAdapter adapter;

    public static ListaHorariosFragment nuevaInstancia(Cancha cancha, Date dia, int posicionDia) {
        ListaHorariosFragment fragment = new ListaHorariosFragment();

        Bundle args = new Bundle();
        args.putSerializable(ARG_CANCHA, cancha);
        args.putSerializable(ARG_DIA, dia);
        args.putInt(ARG_POSICION_DIA, posicionDia);
        args.putSerializable(ARG_HORARIOS, (Serializable) datosHorarios(posicionDia));
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

        // Appbar
        if (primerDia()) {
            fecha.setText(getResources().getString(R.string.txtHorariosFechaHoy));
            flechaDiaAnterior.setVisibility(INVISIBLE);
        } else {
            fecha.setText(getTextoFecha());
            if (ultimoDia()) {
                flechaDiaSiguiente.setVisibility(INVISIBLE);
            }
        }

        // Recycler view
        layoutManager = new LinearLayoutManager(getActivity());
        horariosRecyclerView.setLayoutManager(layoutManager);

        // Adapter
        adapter = new HorariosAdapter(horarios());
        horariosRecyclerView.setAdapter(adapter);

    }

    private String getTextoFecha() {
        Date dia = (Date) getArguments().getSerializable(ARG_DIA);
        return textoDia(dia);
    }

    private boolean primerDia() {
        return getArguments().getInt(ARG_POSICION_DIA) == 0;
    }

    private boolean ultimoDia() {
        return getArguments().getInt(ARG_POSICION_DIA) == 7;
    }

    private static List<SlotReserva> datosHorarios(int dia) {
        Servidor servidor = Servidor.instancia();
        return servidor.getHorarios(servidor.getClub().getRangoHorario(), dia);
    }

    private List<SlotReserva> horarios() {
        return (List<SlotReserva>) getArguments().getSerializable(ARG_HORARIOS);
    }

    private Cancha getCancha() {
        return (Cancha) getArguments().getSerializable(ARG_CANCHA);
    }

}