package com.santiago.canchaapp.app.fragment;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.santiago.canchaapp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RegistrarCanchasFragment extends Fragment {

    @BindView(R.id.fab)
    public FloatingActionButton fab;

    public static RegistrarCanchasFragment nuevaInstancia() {
        return new RegistrarCanchasFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_registrar_canchas, container, false);

        ButterKnife.bind(this, view);

        return view;
    }
}
