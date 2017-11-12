package com.santiago.canchaapp.app.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.viewholder.HorarioViewHolder;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Club;
import com.santiago.canchaapp.dominio.EstadoReserva;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.SlotHorarioAlquiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.santiago.canchaapp.dominio.EstadoReserva.*;
import static com.santiago.canchaapp.dominio.Horario.horaDesde;

public class HorariosAdapter extends RecyclerView.Adapter<HorarioViewHolder> {

    private Activity activity;
    private List<SlotHorarioAlquiler> horarios;
    private Cancha cancha;
    private Club club;
    private Date fecha;
    private boolean esMiCancha;
    private Horario rangoHorario;
    private int horaActual;
    private boolean primerDia;

    public HorariosAdapter(Activity activity, Cancha cancha, Club club, Date fecha, boolean esMiCancha, Horario rangoHorario, int horaActual, boolean primerDia) {
        this.activity = activity;
        this.cancha = cancha;
        this.club = club;
        this.esMiCancha = esMiCancha;
        this.fecha = fecha;
        this.rangoHorario = rangoHorario;
        this.horaActual = horaActual;
        this.primerDia = primerDia;
        this.horarios = generarListaDeHorarios(cancha.getRangoHorario());
        notifyDataSetChanged();
    }

    public void actualizarLista(Alquiler alquilerActualizado) {
        if (!rangoHorario.contiene(alquilerActualizado.getHora())) {
             return;
        }
        actualizarHorarios(alquilerActualizado);
    }

    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new HorarioViewHolder(
                activity,
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horario, viewGroup, false),
                cancha,
                club,
                esMiCancha,
                fecha
        );
    }

    @Override
    public void onBindViewHolder(HorarioViewHolder viewHolder, final int position) {
        viewHolder.cargarDatosEnVista(horarios.get(position), primerDia, horaActual);
    }

    @Override
    public int getItemCount() {
        return horarios.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    // Auxiliar
    private List<SlotHorarioAlquiler> generarListaDeHorarios(Horario rangoHorario) {
        List<SlotHorarioAlquiler> horarios = new ArrayList<>();
        for (int h = rangoHorario.getDesde(); h < rangoHorario.getHasta(); h++) {
            horarios.add(new SlotHorarioAlquiler(horaDesde(h), null));
        }
        return horarios;
    }

    private Alquiler reservaEnHorario(int hora, List<Alquiler> alquilers) {
        for (Alquiler alquiler: alquilers) {
            if (alquiler.getHora() == hora) {
                return alquiler;
            }
        }
        return null;
    }

    private void actualizarHorarios(Alquiler alquilerActualizado) {
        Integer i = indiceDeHorario(alquilerActualizado);
        if (i != null) {
            // Se canceló el alquiler => se libera el horario
            if (alquilerActualizado.getEstado() == CANCELADA) {
                horarios.set(i, new SlotHorarioAlquiler(horarios.get(i).getHorario(), null));
            } else { // Se actualizó el alquiler => se reemplaza en la lista
                horarios.set(i, new SlotHorarioAlquiler(horarios.get(i).getHorario(), alquilerActualizado));
            }
            notifyItemChanged(i);
        }
    }

    private Integer indiceDeHorario(Alquiler alquiler) {
        for (int i = 0; i < horarios.size(); i++) {
            if (horarios.get(i).getHorario().getDesde() == alquiler.getHora()) {
                return i;
            }
        }
        return null; // No debería pasar
    }

}
