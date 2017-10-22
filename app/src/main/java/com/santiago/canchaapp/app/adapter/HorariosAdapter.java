package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.viewholder.HorarioViewHolder;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Cancha;
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

    private List<SlotHorarioAlquiler> horarios;

    private List<Alquiler> alquileres;

    private Cancha cancha;

    private Date fecha;

    private boolean esMiCancha;

    private Horario rangoHorario;

    public HorariosAdapter(Cancha cancha, Date fecha, boolean esMiCancha, Horario rangoHorario) {
        this.cancha = cancha;
        this.esMiCancha = esMiCancha;
        this.fecha = fecha;
        this.rangoHorario = rangoHorario;
        this.alquileres = new ArrayList<>();
        this.horarios = generarListaDeHorarios(cancha.getDatosClub().getRangoHorario(), new ArrayList<Alquiler>());
    }

    public void actualizarLista(Alquiler alquilerActualizado) {
        actualizarAlquileres(alquilerActualizado);
        horarios.clear();
        horarios.addAll(generarListaDeHorarios(rangoHorario, alquileres));
        this.notifyDataSetChanged();
    }

    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new HorarioViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horario, viewGroup, false),
                cancha,
                esMiCancha,
                fecha
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

    // Para prevenir bug de items duplicados al scrollear
    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
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
            if (alquiler.getHora() == hora) {
                return alquiler;
            }
        }
        return null;
    }

    private void actualizarAlquileres(Alquiler alquilerActualizado) {
        Integer i = indiceDeAlquiler(alquilerActualizado);
        // Se reservó un horario (y no está cancelado) => se agrega alquiler a la lista
        if (i == null) {
            if (alquilerActualizado.getEstado() != CANCELADA) {
                alquileres.add(alquilerActualizado);
            }
        } else {
            // Se canceló el alquiler => se saca de la lista
            if (alquilerActualizado.getEstado() == CANCELADA) {
                alquileres.remove((int) i);
            } else { // Se actualizó el alquiler => se reemplaza en la lista
                alquileres.set(i, alquilerActualizado);
            }
        }
    }

    private Integer indiceDeAlquiler(Alquiler alquiler) {
        for (int i = 0; i < alquileres.size(); i++) {
            if (Objects.equals(alquileres.get(i).getUuid(), alquiler.getUuid())) {
                return i;
            }
        }
        return null;
    }

}
