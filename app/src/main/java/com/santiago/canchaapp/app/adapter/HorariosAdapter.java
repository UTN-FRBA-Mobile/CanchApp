package com.santiago.canchaapp.app.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.otros.AccionesSobreReserva;
import com.santiago.canchaapp.app.viewholder.HorarioViewHolder;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Cancha;
import com.santiago.canchaapp.dominio.Horario;
import com.santiago.canchaapp.dominio.Reserva;
import com.santiago.canchaapp.dominio.SlotHorarioAlquiler;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static com.santiago.canchaapp.dominio.Horario.horaDesde;

public class HorariosAdapter extends RecyclerView.Adapter<HorarioViewHolder> {

    private List<SlotHorarioAlquiler> horarios;

    private List<Alquiler> alquileres;

    private Cancha cancha;

    private Date fecha;

    private AccionesSobreReserva accionesSobreReserva;

    public HorariosAdapter(Cancha cancha, Date fecha) {
        this.cancha = cancha;
        this.fecha = fecha;
        this.alquileres = new ArrayList<>();
        this.horarios = generarListaDeHorarios(cancha.getDatosClub().getRangoHorario(), new ArrayList<Alquiler>());
    }

    public void actualizarLista(Horario rangoHorario, Alquiler alquilerActualizado) {
        actualizarAlquileres(alquilerActualizado);
        horarios.clear();
        horarios.addAll(generarListaDeHorarios(rangoHorario, alquileres));
        this.notifyDataSetChanged();
    }

    @Override
    public HorarioViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new HorarioViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_horario, viewGroup, false),
                cancha
        );
    }

    @Override
    public void onBindViewHolder(HorarioViewHolder viewHolder, final int position) {
        viewHolder.cargarDatosEnVista(horarios.get(position), fecha);
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
            if (alquiler.getHora() == hora) {
                return alquiler;
            }
        }
        return null;
    }

    private void actualizarAlquileres(Alquiler alquilerActualizado) {
        for (int i = 0; i < alquileres.size(); i++) {
            if (Objects.equals(alquileres.get(i).getUuid(), alquilerActualizado.getUuid())) {
                alquileres.set(i, alquilerActualizado);
                return;
            }
        }
        alquileres.add(alquilerActualizado);
    }

}
