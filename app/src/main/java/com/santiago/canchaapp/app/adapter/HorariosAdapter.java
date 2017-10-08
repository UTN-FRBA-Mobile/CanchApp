package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.viewholder.HorarioViewHolder;
import com.santiago.canchaapp.app.viewholder.ReservaViewHolder;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.SlotReserva;

import java.util.List;

public class HorariosAdapter extends RecyclerView.Adapter<HorarioViewHolder> {

    private List<SlotReserva> horarios;

    private AccionesSobreReserva accionesSobreReserva;

    public HorariosAdapter(List<SlotReserva> horarios) {
        this.horarios = horarios;
    }

    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new HorarioViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horario, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(HorarioViewHolder viewHolder, final int position) {
        viewHolder.cargarDatosEnVista(horarios.get(position));
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }

}
