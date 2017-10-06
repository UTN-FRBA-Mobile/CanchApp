package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.app.adapter.CanchasAdapter;
import com.santiago.canchaapp.dominio.Cancha;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class CanchaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.cancha_foto)
    public ImageView imagenCancha;

    @BindView(R.id.cancha_nombre)
    public TextView textoNombre;

    @BindView(R.id.cancha_superficie)
    public TextView textoSuperficie;

    @BindView(R.id.cancha_extra)
    public TextView textoExtra;

    @BindView(R.id.item_contenido_cancha)
    public LinearLayout contenido;

    private View view;

    private CanchasAdapter adapter;

    public CanchaViewHolder(View v, CanchasAdapter adapter) {
        super(v);
        ButterKnife.bind(this, v);
        this.view = v;
        this.adapter = adapter;
    }

    public void cargarDatosEnVista(Cancha cancha) {
        // Setea textos
        textoNombre.setText(cancha.getNombre() + " - " + cancha.getTipoCancha().nombre);
        textoSuperficie.setText(view.getResources().getString(R.string.txtCanchaSuperficie, cancha.getSuperficie().nombre));
        if (cancha.esTechada()) {
            textoExtra.setText(view.getResources().getString(R.string.txtCanchaTechada));
        } else {
            textoExtra.setVisibility(GONE);
        }

        // Setea imagen
        if (cancha.tieneFotos()) {
            Picasso.with(view.getContext())
                    .load(cancha.getFotoPrincipalUrl())
                    .placeholder(R.drawable.cancha_sin_foto)
                    .fit().centerCrop().into(imagenCancha);
        } else {
            Picasso.with(view.getContext())
                    .load(R.drawable.cancha_sin_foto)
                    .fit().centerCrop().into(imagenCancha);
        }


        // Setea boton
        contenido.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        adapter.onClick(v, getAdapterPosition());
    }

}
