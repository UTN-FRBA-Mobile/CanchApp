package com.santiago.canchaapp.app.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.santiago.canchaapp.R;
import com.santiago.canchaapp.dominio.Cancha;

import butterknife.BindView;
import butterknife.ButterKnife;

import static android.view.View.GONE;

public class CanchaViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.cancha_nombre)
    public TextView textoNombre;

    @BindView(R.id.cancha_superficie)
    public TextView textoSuperficie;

    @BindView(R.id.cancha_extra)
    public TextView textoExtra;

    private View view;

    public CanchaViewHolder(View v) {
        super(v);
        ButterKnife.bind(this, v);
        view = v;
    }

    public void cargarDatosEnVista(Cancha cancha) {
        textoNombre.setText(cancha.getNombre() + " - " + cancha.getTipoCancha().nombre);
        textoSuperficie.setText(view.getResources().getString(R.string.txtCanchaSuperficie, cancha.getSuperficie()));
        if (cancha.esTechada()) {
            textoExtra.setText(view.getResources().getString(R.string.txtCanchaTechada));
        } else {
            textoExtra.setVisibility(GONE);
        }
    }

}
