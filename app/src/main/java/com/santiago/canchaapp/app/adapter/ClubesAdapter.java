package com.santiago.canchaapp.app.adapter;

import android.content.Context;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.fragment.ClubFragment;
import com.santiago.canchaapp.app.otros.RecyclerViewOnItemClickListener;
import com.santiago.canchaapp.app.viewholder.ClubViewHolder;
import com.santiago.canchaapp.dominio.Alquiler;
import com.santiago.canchaapp.dominio.Club;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.santiago.canchaapp.app.otros.FragmentTags.CLUB;
import static com.santiago.canchaapp.dominio.EstadoReserva.CANCELADA;

public class ClubesAdapter extends RecyclerView.Adapter<ClubViewHolder> implements RecyclerViewOnItemClickListener {

    private List<Club> clubes;

    private Context context;

    public ClubesAdapter(Context context) {
        this.clubes = new ArrayList<>();
        this.context = context;
    }

    @Override
    public ClubViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        return new ClubViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_club, viewGroup, false),
                this
        );
    }

    public void actualizarLista(Club club) {
        actualizarClubes(club);
        this.notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(ClubViewHolder viewHolder, int position) {
        viewHolder.cargarDatosEnVista(clubes.get(position));
    }

    @Override
    public int getItemCount() {
        return clubes.size();
    }

    @Override
    public void onClick(View v, final int posicion) {
       new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction()
                        .replace(R.id.content_frame, ClubFragment.nuevaInstancia(clubes.get(posicion).getUuid(), false), CLUB.toString())
                        .addToBackStack(null)
                        .commit();
            }
        }, 250);
    }

    private void actualizarClubes(Club club) {
        Integer i = indiceDeClub(club);
        if (i == null) {
            clubes.add(club);
        } else {
            clubes.set(i, club);
        }
    }

    private Integer indiceDeClub(Club club) {
        for (int i = 0; i < clubes.size(); i++) {
            if (Objects.equals(clubes.get(i).getUuid(), club.getUuid())) {
                return i;
            }
        }
        return null;
    }
}