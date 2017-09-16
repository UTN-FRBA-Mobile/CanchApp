package com.santiago.canchaapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;

/**
 * Created by Santiago on 14/9/2017.
 */

public class DialogoTengoClub extends DialogFragment{
    public interface DialogoTengoClubListener {
        void onDialogPositiveClick(Boolean mostrarSeccionClub);
        void onDialogNegativeClick();
    }
    DialogoTengoClubListener mListener;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (DialogoTengoClubListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement NoticeDialogListener");
        }
    }
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialogo_tengo_club, null);
        final CheckBox checkBox = dialogView.findViewById(R.id.checkBoxTengoClub);
        final String[] item = {getString(R.string.txtDialogoTengoClub)};
        builder.setTitle("Información")
                .setPositiveButton("Ingresar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogPositiveClick(checkBox.isChecked());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onDialogNegativeClick();
                    }
                })
                .setView(dialogView);
        return builder.create();
    }
}
