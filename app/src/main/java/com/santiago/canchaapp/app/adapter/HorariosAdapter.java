package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.viewholder.HorarioViewHolder;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.SlotHorarioAlquiler;

import java.util.ArrayList;
import java.util.List;

import static com.santiago.canchaapp.dominio.Horario.horaDesde;

public class HorariosAdapter extends RecyclerView.Adapter<HorarioViewHolder> {

    private List<SlotHorarioAlquiler> horarios;

    private AccionesSobreReserva accionesSobreReserva;

    public HorariosAdapter(Horario rangoHorario) {
        this.horarios = generarListaDeHorarios(rangoHorario, new ArrayList<Alquiler>());
    }

    public void actualizarLista(Horario rangoHorario, List<Alquiler> alquileres) {
        horarios.clear();
        horarios.addAll(generarListaDeHorarios(rangoHorario, alquileres));
        this.notifyDataSetChanged();
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

    // Auxiliar

    private List<SlotHorarioAlquiler> generarListaDeHorarios(Horario rangoHorario, List<Alquiler> alquileres) {
        List<SlotHorarioAlquiler> horarios = new ArrayList<>();
        for (int h = rangoHorario.getDesde(); h < rangoHorario.getHasta(); h++) {
            horarios.add(new SlotHorarioAlquiler(horaDesde(h), reservaEnHorario(h, alquileres)));
        }
        return horarios;
    }

    private Alquiler reservaEnHorario(int hora, List<Alquiler> alquilers) {
        for (Alquiler alquiler: alquilers) {
            if (alquiler.getHorario().getDesde() == hora) {
                return alquiler;
            }
        }
        return null;
    }

}
